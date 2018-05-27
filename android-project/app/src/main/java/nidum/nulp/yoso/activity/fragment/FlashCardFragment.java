package nidum.nulp.yoso.activity.fragment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.eftimoff.androipathview.PathView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import nidum.nulp.yoso.activity.KanaAnimationActivity;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.kanjivg.KanaPathProvider;
import nidum.nulp.yoso_project.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static nidum.nulp.yoso.kanjivg.DurationGenerator.getDuration;

public class FlashCardFragment extends Fragment {
    private Context context;
    private FrameLayout layout;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    private KanaPathProvider kanaPathProvider;
    private TextView kanjiView;
    private TextView meaningView;
    private TextView onView;
    private TextView kunView;
    private ImageView levelImg;

    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private FloatingActionButton fab;

    private boolean animationFinished = true;
    private Kana kana;
    private boolean isHiragana;
    private boolean singleReading = true;

    public FlashCardFragment() {

    }

    public static FlashCardFragment newInstance(Kana kana, boolean isHiragana) {
        FlashCardFragment fragment = new FlashCardFragment();
        fragment.kana = kana;
        fragment.isHiragana = isHiragana;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View fragmentView = inflater.inflate(R.layout.fragment_flash_card, container, false);// Inflate the layout for this fragment
        mCardBackLayout = fragmentView.findViewById(R.id.card_back);
        mCardFrontLayout = fragmentView.findViewById(R.id.card_front);
        kanjiView = fragmentView.findViewById(R.id.kanjiView);
        meaningView = fragmentView.findViewById(R.id.meaningView);
        onView = fragmentView.findViewById(R.id.onView);
        kunView = fragmentView.findViewById(R.id.kunView);
        levelImg = fragmentView.findViewById(R.id.levelImg);
        layout = fragmentView.findViewById(R.id.layout);

        final FlashCardFragment that = this;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.flipCard(null);
            }
        });

        kanaPathProvider = new KanaPathProvider(fragmentView.getContext());

        loadAnimations(fragmentView.getContext());
        changeCameraDistance();

        PathView pathView = fragmentView.findViewById(R.id.path_view);
        layout = fragmentView.findViewById(R.id.animation_layout);
        List<Path> paths = null;
        try {
            if (isHiragana) {
                paths = kanaPathProvider.buildPaths(kana.getHiragana().charAt(0), 350);
            } else {
                paths = kanaPathProvider.buildPaths(kana.getKatakana().charAt(0), 350);
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        initPathView(pathView, paths);
        initText();

        return fragmentView;
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations(Context context) {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.in_animation);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            fab.setVisibility(View.INVISIBLE);
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            fab.setVisibility(View.VISIBLE);
            mIsBackVisible = false;
        }
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
        final FlashCardFragment that = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.flipCard(null);
            }
        });
    }

    private void initText() {
        if (singleReading) {
            if (isHiragana) {
                kanjiView.setText(kana.getHiragana());
            } else {
                kanjiView.setText(kana.getKatakana());
            }
            onView.setText(kana.getReading());
            onView.setTextSize(36);
            kunView.setVisibility(View.INVISIBLE);
            meaningView.setVisibility(View.INVISIBLE);
            kanjiView.setPadding(0, 350, 0, 0);
            levelImg.setPadding(0, 350, 0, 0);
        } else {
            // TODO
        }
    }

    private void initPathView(PathView pathView, final List<Path> paths) {
        final float duration = getDuration(paths, 1.1f);

        pathView.setPathWidth(16);
        pathView.setPaths(paths);
        pathView.setPathColor(R.color.colorPrimary);

        //animate(pathView, 10);
        pathView.getPathAnimator()
                .duration(0)
                .start();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationFinished) {
                    FrameLayout drawingView = ((KanaAnimationActivity) context).findViewById(R.id.card_front);
                    drawingView.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View childLayout = inflater.inflate(R.layout.card_front,
                            null);
                    drawingView.addView(childLayout);

                    PathView pathView = ((KanaAnimationActivity) context).findViewById(R.id.path_view);
                    initPathView(pathView, paths);
                    animate(pathView, duration);
                }
            }
        });
    }

    private void animate(PathView pathView, float duration) {
        pathView.getSequentialPathAnimator()
                .delay(300)
                .duration((int) duration)
                .interpolator(new AccelerateDecelerateInterpolator())
                .listenerStart(new PathView.AnimatorBuilder.ListenerStart() {
                    @Override
                    public void onAnimationStart() {
                        animationFinished = false;
                    }
                })
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        animationFinished = true;
                    }
                })
                .start();

    }

}
