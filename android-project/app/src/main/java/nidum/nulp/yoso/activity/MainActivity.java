package nidum.nulp.yoso.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import nidum.nulp.yoso.activity.fragment.KanaFragment;
import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.KanaRepository;
import nidum.nulp.yoso.utill.ResourceLoader;
import nidum.nulp.yoso_project.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private TableLayout mKanaTable;

    private KanaRepository kanaRepository;
    private boolean isHiragana = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResourceLoader.loadResources(this);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationUtil.initNavigation(this, this);

        kanaRepository = new KanaRepository(new DBHelper(this));
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
            }
        });

    }

    private void initKanaTable() {
        List<Kana> allKana = kanaRepository.getAllKana();

        for (int i = 0; i < allKana.size(); i++) {
            for (int j = 0; j < allKana.size(); j++) {
                if (allKana.get(i).getOrder() < allKana.get(j).getOrder()) {
                    Kana tmp = allKana.get(i);
                    allKana.set(i, allKana.get(j));
                    allKana.set(j, tmp);
                }
            }
        }

        for (int i = 0, j = 0; i < allKana.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);
            mKanaTable.addView(row, j);

            row.setId(View.generateViewId());
            FragmentManager fragmentManager = getFragmentManager();
            do {
                Kana kana = allKana.get(i);

                String first;
                String second;

                if (isHiragana) {
                    first = kana.getHiragana();
                    second = kana.getKatakana();
                } else {
                    first = kana.getKatakana();
                    second = kana.getHiragana();
                }

                Fragment fragment = KanaFragment.newInstance(R.drawable.sakura_fine, first, second, kana.getReading());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(row.getId(), fragment);
                fragmentTransaction.commit();

                i++;
                if (i == allKana.size() - 1) {
                    break;
                }
            } while (i < allKana.size() && !allKana.get(i).getReading().endsWith("a"));

            j++;
            i--;
        }
    }

}
