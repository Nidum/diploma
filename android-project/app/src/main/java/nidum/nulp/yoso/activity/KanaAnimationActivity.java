package nidum.nulp.yoso.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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

    private String currentKana;
    private String currentKanaReading;
    private boolean animationFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kana_animation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        kanaPathProvider = new KanaPathProvider(this);
        currentKana = getIntent().getStringExtra(KanaFragment.ARG_KANA_CURRENT);
        currentKanaReading = getIntent().getStringExtra(KanaFragment.ARG_KANA_READING);
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
     //   TextView readingView = findViewById(R.id.reading_text_view);
      //  readingView.setText(currentKanaReading);

        initPathView(pathView, paths);
    }

    private void initPathView(PathView pathView, final List<Path> paths) {
        float duration = getDuration(paths, 1.1f);

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

        pathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PathView animationView = parent.findViewById(R.id.path_view);
                if (animationFinished) {
                    animationView.setId(View.generateViewId());
                    layout.removeView(animationView);

                    PathView pathView = new PathView(parent);
                    pathView.setId(R.id.path_view);
                    initPathView(pathView, paths);
                    layout.addView(pathView, 1);

                    Resources resources = getApplicationContext().getResources();

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    int margin = resources.getDimensionPixelSize(R.dimen.common_margin);
                    params.setMargins(margin, margin, margin, margin);

                    //ViewGroup.LayoutParams layoutParams = pathView.getLayoutParams();
                    params.height = resources.getDimensionPixelSize(R.dimen.kana_drawing_canvas_height);

                    pathView.setLayoutParams(params);
                }
            }
        });
    }

}
