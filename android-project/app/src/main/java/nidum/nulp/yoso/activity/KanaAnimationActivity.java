package nidum.nulp.yoso.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eftimoff.androipathview.PathView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import nidum.nulp.yoso.activity.fragment.KanaFragment;
import nidum.nulp.yoso.kanjivg.KanaPathProvider;
import nidum.nulp.yoso_project.R;

import static nidum.nulp.yoso.kanjivg.DurationGenerator.getDuration;

public class KanaAnimationActivity extends AppCompatActivity {
    private LinearLayout layout;
    private KanaPathProvider kanaPathProvider;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private TextView kanjiView;
    private TextView meaningView;
    private TextView onView;
    private TextView kunView;
    private FloatingActionButton playFab;
    private ImageView levelImg;

    private String currentKana;
    private String readingKana;
    private boolean singleReading;
    private boolean animationFinished = false;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kana_animation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        kanaPathProvider = new KanaPathProvider(this);

        Intent intent = getIntent();
        currentKana = intent.getStringExtra(KanaFragment.ARG_KANA_CURRENT);
        readingKana = intent.getStringExtra(KanaFragment.ARG_KANA_READING);
        singleReading = intent.getBooleanExtra(KanaFragment.IS_KANA, true);

        findViews();
        loadAnimations();
        changeCameraDistance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PathView pathView = this.findViewById(R.id.path_view);
        layout = findViewById(R.id.animation_layout);
        List<Path> paths = null;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            paths = kanaPathProvider.buildPaths(currentKana.charAt(0), width);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        initPathView(pathView, paths);
        initText();
    }

    private void initText(){
        if(singleReading){
            kanjiView.setText(currentKana);
            onView.setText(readingKana);
            onView.setTextSize(36);
            kunView.setVisibility(View.INVISIBLE);
            meaningView.setVisibility(View.INVISIBLE);
            kanjiView.setPadding(0, 250, 0, 0);
            levelImg.setPadding(0, 250, 0, 0);
        } else {

        }
    }

    private void initPathView(PathView pathView, final List<Path> paths) {
        final float duration = getDuration(paths, 1.1f);

        pathView.setPathWidth(16);
        pathView.setPaths(paths);
        pathView.setPathColor(R.color.colorPrimary);

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

        final Activity parent = this;

        playFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationFinished) {
                    FrameLayout drawingView = findViewById(R.id.card_front);
                    drawingView.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) parent.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View childLayout = inflater.inflate(R.layout.card_front,
                           null);
                    drawingView.addView(childLayout);

                    PathView pathView = parent.findViewById(R.id.path_view);
                    initPathView(pathView, paths);
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
        });
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
        playFab = findViewById(R.id.fab);
        kanjiView  = findViewById(R.id.kanjiView);
        meaningView = findViewById(R.id.meaningView);
        onView = findViewById(R.id.onView);
        kunView = findViewById(R.id.kunView);
        levelImg = findViewById(R.id.levelImg);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            playFab.setVisibility(View.INVISIBLE);
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            playFab.setVisibility(View.VISIBLE);
            mIsBackVisible = false;
        }
    }

}
