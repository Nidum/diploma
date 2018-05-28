package nidum.nulp.yoso.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import nidum.nulp.yoso.activity.fragment.KanaFragment;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.KanaRepository;
import nidum.nulp.yoso_project.R;

public class HieroglyphAnimationActivity extends AppCompatActivity {
    private ViewPager pager;
    private KanaFragmentPagerAdapter pagerAdapter;
    private Button rememberBtn;
    private Button forgotBtn;

    private KanaRepository kanaRepository;
    private boolean isHiragana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kana_animation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        isHiragana = intent.getBooleanExtra(KanaFragment.IS_HIRAGANA, true);
        int position = intent.getIntExtra(KanaFragment.ARG_KANA_ORDER, 0) - 1;

        findViews();
        kanaRepository = new KanaRepository(new DBHelper(this));
        pagerAdapter = new KanaFragmentPagerAdapter(getSupportFragmentManager(), kanaRepository, isHiragana);

        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(position);

        setListeners();
        manageRememberLogic();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void findViews() {
        pager = findViewById(R.id.pager);
        rememberBtn = findViewById(R.id.remember_btn);
        forgotBtn = findViewById(R.id.forgot_btn);
    }

    private void setListeners() {
        HieroglyphAnimationActivity that = this;

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kana kana = pagerAdapter.getKanaList().get(pager.getCurrentItem());
                if (isHiragana) {
                    kana.setHiraganaLastReviewed(-1);
                    kana.setHiraganaStudyLevel(StudyLevel.NONE);
                } else {
                    kana.setKatakanaLastReviewed(-1);
                    kana.setKatakanaStudyLevel(StudyLevel.NONE);
                }
                kanaRepository.updateKanaStudyData(kana);
                manageRememberLogic();
            }
        });

        rememberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kana kana = pagerAdapter.getKanaList().get(pager.getCurrentItem());
                if (isHiragana) {
                    kana.setHiraganaLastReviewed(new Date().getTime());
                    StudyLevel studyLevel = StudyLevel.values()[kana.getHiraganaStudyLevel().ordinal() + 1];
                    kana.setHiraganaStudyLevel(studyLevel);
                } else {
                    kana.setKatakanaLastReviewed(new Date().getTime());
                    StudyLevel studyLevel = StudyLevel.values()[kana.getKatakanaStudyLevel().ordinal() + 1];
                    kana.setKatakanaStudyLevel(studyLevel);
                }
                kanaRepository.updateKanaStudyData(kana);
                manageRememberLogic();
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                System.out.println(String.format("Position: %d, Position offset: %f, positionOffsetPixels: %d", position, positionOffset, positionOffsetPixels));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                manageRememberLogic();
            }
        });
    }

    void manageRememberLogic(){
        int currentItem = pager.getCurrentItem();
        Kana kana = pagerAdapter.getKanaList().get(currentItem);

        long lastReviewed;
        StudyLevel studyLevel;

        if (isHiragana) {
            lastReviewed = kana.getHiraganaLastReviewed();
            studyLevel = kana.getHiraganaStudyLevel();
        } else {
            lastReviewed = kana.getKatakanaLastReviewed();
            studyLevel = kana.getKatakanaStudyLevel();
        }

        long timeToNextReview = studyLevel.getTimeToNextReview();

        long now = new Date().getTime();
        if(!(now > lastReviewed + timeToNextReview)){
            rememberBtn.setClickable(false);
            rememberBtn.setHint("Next review in X days");
            rememberBtn.setText("Review in X days");
        } else {
            rememberBtn.setClickable(true);
            rememberBtn.setHint("");
            rememberBtn.setText("Remember");
        }
    }
}
