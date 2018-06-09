package nidum.nulp.yoso.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

import nidum.nulp.yoso.activity.fragment.KanaFragment;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.enumeration.NoryokuLevel;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.KanaRepository;
import nidum.nulp.yoso.repository.KanjiRepository;
import nidum.nulp.yoso.utill.ResourceLoader;
import nidum.nulp.yoso_project.R;

import static java.lang.String.format;
import static nidum.nulp.yoso.entity.enumeration.StudyLevel.FINE;
import static nidum.nulp.yoso.entity.enumeration.StudyLevel.LOW;
import static nidum.nulp.yoso.entity.enumeration.StudyLevel.MASTERED;
import static nidum.nulp.yoso.entity.enumeration.StudyLevel.MIDDLE;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private TableLayout mKanaTable;

    private KanaRepository kanaRepository;
    private KanjiRepository kanjiRepository;
    private boolean isHiragana = true;
    List<KanaFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResourceLoader.loadResources(this);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationUtil.initNavigation(this, this);

        DBHelper dbHelper = new DBHelper(this);
        kanaRepository = new KanaRepository(dbHelper);
        kanjiRepository = new KanjiRepository(dbHelper, NoryokuLevel.ALL);
        mKanaTable = findViewById(R.id.kana_table);
        initKanaTable();

        FloatingActionButton mSwitch = findViewById(R.id.switch_btn);
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHiragana = !isHiragana;
                for (int i = 0; i < mKanaTable.getChildCount(); i++) {
                    TableRow row = (TableRow) mKanaTable.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        TextView current = row.getChildAt(j).findViewById(R.id.current_kana_text_view);
                        TextView other = row.getChildAt(j).findViewById(R.id.other_kana_text_view);
                        CharSequence tmp = current.getText();
                        current.setText(other.getText());
                        other.setText(tmp);
                    }
                }
                for (KanaFragment fragment : fragments) {
                    fragment.updateIsHiragana();
                }
            }
        });

        initProgressMenu();
    }

    private void initKanaTable() {
        List<Kana> allKana = kanaRepository.getAll();

        Collections.sort(allKana, new Comparator<Kana>() {
            @Override
            public int compare(Kana o1, Kana o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });

        for (int i = 0, j = 0; i < allKana.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);
            mKanaTable.addView(row, j);

            row.setId(View.generateViewId());
            FragmentManager fragmentManager = getFragmentManager();
            do {
                Kana kana = allKana.get(i);

                KanaFragment fragment = KanaFragment.newInstance(kana, this.isHiragana);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(row.getId(), fragment);
                fragmentTransaction.commit();
                fragments.add(fragment);
                i++;
                if (i == allKana.size() - 1) {
                    break;
                }
            } while (i < allKana.size() && !allKana.get(i).getReading().endsWith("a"));

            j++;
            i--;
        }
    }

    private void initProgressMenu(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        EnumMap<StudyLevel, Integer> studyLevelIntegerEnumMap = kanjiRepository.countByLevel();

        TextView lowProgress = navigationView.getHeaderView(0).findViewById(R.id.low_progress);
        lowProgress.setText(format("%d", studyLevelIntegerEnumMap.get(LOW)));
        TextView midProgress = navigationView.getHeaderView(0).findViewById(R.id.middle_progress);
        midProgress.setText(format("%d", studyLevelIntegerEnumMap.get(MIDDLE)));
        TextView fineProgress = navigationView.getHeaderView(0).findViewById(R.id.fine_progress);
        fineProgress.setText(format("%d", studyLevelIntegerEnumMap.get(FINE)));
        TextView masteredProgress = navigationView.getHeaderView(0).findViewById(R.id.mastered_progress);
        masteredProgress.setText(format("%d", studyLevelIntegerEnumMap.get(MASTERED)));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
