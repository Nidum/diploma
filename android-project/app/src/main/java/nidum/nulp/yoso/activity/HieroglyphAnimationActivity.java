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
import nidum.nulp.yoso.entity.Hieroglyph;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.Kanji;
import nidum.nulp.yoso.entity.Radical;
import nidum.nulp.yoso.entity.enumeration.EntityType;
import nidum.nulp.yoso.entity.enumeration.NoryokuLevel;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.KanaRepository;
import nidum.nulp.yoso.repository.KanjiRepository;
import nidum.nulp.yoso.repository.RadicalsRepository;
import nidum.nulp.yoso.repository.Repository;
import nidum.nulp.yoso_project.R;

import static nidum.nulp.yoso.utill.IntentHolder.ARG_HIEROGLYPH_TYPE;
import static nidum.nulp.yoso.utill.IntentHolder.ARG_NORYOKU_LEVEL;
import static nidum.nulp.yoso.utill.IntentHolder.ARG_ORDER;

public class HieroglyphAnimationActivity extends AppCompatActivity {
    private ViewPager pager;
    private HieroglyphFragmentPagerAdapter pagerAdapter;
    private Button rememberBtn;
    private Button forgotBtn;

    private Repository repository;
    private EntityType entityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kana_animation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        entityType = EntityType.valueOf(intent.getStringExtra(ARG_HIEROGLYPH_TYPE));
        int position = intent.getIntExtra(ARG_ORDER, 0) - 1;

        findViews();
        DBHelper dbHelper = new DBHelper(this);
        switch (entityType) {
            case HIRAGANA:
            case KATAKANA:
                repository = new KanaRepository(dbHelper);
                break;
            case KANJI:
                String level = intent.getStringExtra(ARG_NORYOKU_LEVEL);
                repository = new KanjiRepository(dbHelper, NoryokuLevel.valueOf(level));
                break;
            case RADICAL:
                repository = new RadicalsRepository(dbHelper);
                break;
        }
        pagerAdapter = new HieroglyphFragmentPagerAdapter(getSupportFragmentManager(), repository, entityType);

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
                Hieroglyph hieroglyph = (Hieroglyph) pagerAdapter.getDataList().get(pager.getCurrentItem());
                switch (entityType) {
                    case HIRAGANA: {
                        Kana kana = (Kana) hieroglyph;
                        kana.setHiraganaLastReviewed(-1);
                        kana.setHiraganaStudyLevel(StudyLevel.NONE);
                        break;
                    }
                    case KATAKANA: {
                        Kana kana = (Kana) hieroglyph;
                        kana.setKatakanaLastReviewed(-1);
                        kana.setKatakanaStudyLevel(StudyLevel.NONE);
                        break;
                    }
                    case KANJI:
                        Kanji kanji = (Kanji) hieroglyph;
                        kanji.setLastReviewed(-1);
                        kanji.setStudyLevel(StudyLevel.NONE);
                        break;
                    case RADICAL:
                        Radical radical = (Radical) hieroglyph;
                        radical.setLastReviewed(-1);
                        radical.setStudyLevel(StudyLevel.NONE);
                        break;
                }

                repository.updateStudyData(hieroglyph);
                manageRememberLogic();
            }
        });

        rememberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hieroglyph hieroglyph = (Hieroglyph) pagerAdapter.getDataList().get(pager.getCurrentItem());
                StudyLevel studyLevel;
                switch (entityType) {
                    case HIRAGANA: {
                        Kana kana = (Kana) hieroglyph;
                        kana.setHiraganaLastReviewed(new Date().getTime());
                        studyLevel = StudyLevel.values()[kana.getHiraganaStudyLevel().ordinal() + 1];
                        kana.setHiraganaStudyLevel(studyLevel);
                        break;
                    }
                    case KATAKANA: {
                        Kana kana = (Kana) hieroglyph;
                        kana.setKatakanaLastReviewed(new Date().getTime());
                        studyLevel = StudyLevel.values()[kana.getKatakanaStudyLevel().ordinal() + 1];
                        kana.setKatakanaStudyLevel(studyLevel);
                        break;
                    }
                    case KANJI:
                        Kanji kanji = (Kanji) hieroglyph;
                        kanji.setLastReviewed(new Date().getTime());
                        studyLevel = StudyLevel.values()[kanji.getStudyLevel().ordinal() + 1];
                        kanji.setStudyLevel(studyLevel);
                        break;
                    case RADICAL:
                        Radical radical = (Radical) hieroglyph;
                        radical.setLastReviewed(new Date().getTime());
                        studyLevel = StudyLevel.values()[radical.getStudyLevel().ordinal() + 1];
                        radical.setStudyLevel(studyLevel);
                        break;
                }
                repository.updateStudyData(hieroglyph);
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

    void manageRememberLogic() {
        int currentItem = pager.getCurrentItem();
        Hieroglyph hieroglyph = (Hieroglyph) pagerAdapter.getDataList().get(currentItem);

        long lastReviewed = 0L;
        StudyLevel studyLevel = StudyLevel.NONE;

        switch (entityType) {
            case HIRAGANA: {
                Kana kana = (Kana) hieroglyph;
                lastReviewed = kana.getHiraganaLastReviewed();
                studyLevel = kana.getHiraganaStudyLevel();
                break;
            }
            case KATAKANA: {
                Kana kana = (Kana) hieroglyph;
                lastReviewed = kana.getKatakanaLastReviewed();
                studyLevel = kana.getKatakanaStudyLevel();
                break;
            }
            case KANJI: {
                Kanji kanji = (Kanji) hieroglyph;
                lastReviewed = kanji.getLastReviewed();
                studyLevel = kanji.getStudyLevel();
                break;
            }
            case RADICAL: {
                Radical radical = (Radical) hieroglyph;
                lastReviewed = radical.getLastReviewed();
                studyLevel = radical.getStudyLevel();
                break;
            }
        }

        long timeToNextReview = studyLevel.getTimeToNextReview();

        long now = new Date().getTime();
        if (!(now > lastReviewed + timeToNextReview)) {
            rememberBtn.setClickable(false);
            rememberBtn.setHint("Next review in X days");
            rememberBtn.setText("Review in 3 days");
        } else {
            rememberBtn.setClickable(true);
            rememberBtn.setHint("");
            rememberBtn.setText("Remember");
        }
    }
}
