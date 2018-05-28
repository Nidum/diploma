package nidum.nulp.yoso.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import nidum.nulp.yoso.activity.fragment.KanaFragment;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.KanaRepository;
import nidum.nulp.yoso_project.R;

public class KanaAnimationActivity extends AppCompatActivity {
    private FloatingActionButton playFab;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kana_animation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        boolean isHiragana = intent.getBooleanExtra(KanaFragment.IS_HIRAGANA, true);
        int position = intent.getIntExtra(KanaFragment.ARG_KANA_ORDER, 0) - 1;

        findViews();
        pagerAdapter = new KanaFragmentPagerAdapter(getSupportFragmentManager(), playFab, new KanaRepository(new DBHelper(this)), isHiragana);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(position);
        pager.setOffscreenPageLimit(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void findViews() {
        playFab = findViewById(R.id.fab);
        pager = findViewById(R.id.pager);
    }
}
