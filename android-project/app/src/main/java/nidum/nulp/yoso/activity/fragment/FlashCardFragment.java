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

import nidum.nulp.yoso.entity.Hieroglyph;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.Kanji;
import nidum.nulp.yoso.entity.Radical;
import nidum.nulp.yoso.entity.enumeration.EntityType;
import nidum.nulp.yoso.kanjivg.HieroglyphPathProvider;
import nidum.nulp.yoso_project.R;

import static nidum.nulp.yoso.kanjivg.DurationGenerator.getDuration;

public class FlashCardFragment extends Fragment {
    private boolean animationFinished = true;

    private Context context;
    private FrameLayout layout;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    private HieroglyphPathProvider pathProvider;
    private TextView kanjiView;
    private TextView meaningView;
    private TextView onView;
    private TextView kunView;
    private ImageView levelImg;

    private boolean mIsBackVisible = false;
    private FrameLayout mCardFrontLayout;
    private FrameLayout mCardBackLayout;
    private FloatingActionButton fab;

    private Hieroglyph hieroglyph;
    private EntityType entityType;

    private List<Path> paths;
    private float duration;

    public FlashCardFragment() {
    }

    public static FlashCardFragment newInstance(Hieroglyph hieroglyph, EntityType entityType) {
        FlashCardFragment fragment = new FlashCardFragment();
        fragment.hieroglyph = hieroglyph;
        fragment.entityType = entityType;
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
        View fragmentView = inflater.inflate(R.layout.fragment_flash_card, container, false);
        findViews(fragmentView);

        final FlashCardFragment that = this;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                that.flipCard(null);
            }
        });

        loadAnimations(fragmentView.getContext());
        changeCameraDistance();

        PathView pathView = fragmentView.findViewById(R.id.path_view);
        layout = fragmentView.findViewById(R.id.animation_layout);
        pathProvider = new HieroglyphPathProvider(context);

        try {
            switch (entityType) {
                case HIRAGANA:
                    paths = pathProvider.buildPaths(((Kana)hieroglyph).getHiragana().charAt(0), 350);
                    break;
                case KATAKANA:
                    paths = pathProvider.buildPaths(((Kana)hieroglyph).getKatakana().charAt(0), 350);
                    break;
                case RADICAL:
                    paths = pathProvider.buildPaths(((Radical)hieroglyph).getRadical().charAt(0), 350);
                    break;
                case KANJI:
                    paths = pathProvider.buildPaths(((Kanji)hieroglyph).getKanji().charAt(0), 350);
                    break;
            }
            duration = getDuration(paths, 1.1f);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        initPathView(pathView);
        setFabClickListener();
        pathView.getPathAnimator()
                .duration(0)
                .start();
        initText();

        return fragmentView;
    }

    private void findViews(View fragmentView) {
        mCardBackLayout = fragmentView.findViewById(R.id.card_back);
        mCardFrontLayout = fragmentView.findViewById(R.id.card_front);
        kanjiView = fragmentView.findViewById(R.id.kanjiView);
        meaningView = fragmentView.findViewById(R.id.meaningView);
        onView = fragmentView.findViewById(R.id.onView);
        kunView = fragmentView.findViewById(R.id.kunView);
        levelImg = fragmentView.findViewById(R.id.levelImg);
        layout = fragmentView.findViewById(R.id.layout);
        fab = fragmentView.findViewById(R.id.fab);
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

    private void initPathView(PathView pathView) {
        pathView.setPathWidth(16);
        pathView.setPaths(paths);
        pathView.setPathColor(R.color.colorPrimary);
    }

    public void setFabClickListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationFinished) {
                    PathView pathView = mCardFrontLayout.findViewById(R.id.path_view);
                    animatePath(pathView, duration);
                }
            }
        });
    }

    private void initText() {
        String onReading = "";

        switch (entityType) {
            case HIRAGANA: {
                Kana kana = (Kana) hieroglyph;
                kanjiView.setText(kana.getHiragana());
                onReading = kana.getReading();
                handleSingleReading();
                meaningView.setVisibility(View.INVISIBLE);
                break;
            }
            case KATAKANA: {
                Kana kana = (Kana) hieroglyph;
                kanjiView.setText(kana.getKatakana());
                onReading = kana.getReading();
                handleSingleReading();
                meaningView.setVisibility(View.INVISIBLE);
                break;
            }
            case RADICAL:
                Radical radical = (Radical) hieroglyph;
                kanjiView.setText(radical.getRadical());
                onReading = radical.getReading();
                handleSingleReading();
                meaningView.setText(radical.getMeaning());
                break;
            case KANJI:
                Kanji kanji = (Kanji) hieroglyph;
                kanjiView.setText(kanji.getKanji());
                onView.setText(String.format("On: %s", kanji.getOnyomiReading().replaceAll(" ", "    ")));
                kunView.setText(String.format("Kun: %s", kanji.getKunyomiReading().replaceAll(" ", "    ")));
                meaningView.setText(String.format("Meanings: %s",kanji.getMeaning()));
                handleManyReadings();
                break;
        }

        onView.setText(onReading);
    }

    private void handleSingleReading(){
        onView.setTextSize(36);
        kunView.setVisibility(View.INVISIBLE);
        kanjiView.setPadding(0, 350, 0, 0);
        levelImg.setPadding(0, 350, 0, 0);
    }

    private void handleManyReadings(){
        onView.setTextSize(20);
        kunView.setVisibility(View.VISIBLE);
        onView.setVisibility(View.VISIBLE);
        kanjiView.setPadding(0, 0, 0, 0);
        levelImg.setPadding(0, 0, 0, 0);
    }

    public void animatePath(PathView pathView, float duration) {
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
