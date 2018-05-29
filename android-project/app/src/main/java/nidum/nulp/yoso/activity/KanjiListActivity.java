package nidum.nulp.yoso.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import nidum.nulp.yoso.activity.fragment.KanjiRowFragment;
import nidum.nulp.yoso.entity.Kanji;
import nidum.nulp.yoso.entity.enumeration.EntityType;
import nidum.nulp.yoso.entity.enumeration.NoryokuLevel;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.KanjiRepository;
import nidum.nulp.yoso_project.R;

import static nidum.nulp.yoso.utill.IntentHolder.ARG_NORYOKU_LEVEL;

public class KanjiListActivity extends AppCompatActivity {
    private KanjiRepository kanjiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String level = intent.getStringExtra(ARG_NORYOKU_LEVEL);
        setContentView(R.layout.activity_kanji_list);

        kanjiRepository = new KanjiRepository(new DBHelper(this), NoryokuLevel.valueOf(level));
        initKanji();
    }

    private void initKanji() {
        List<Kanji> allKanji = kanjiRepository.getAll();

        for (int i = 0; i < allKanji.size(); i++) {
            for (int j = 0; j < allKanji.size(); j++) {
                if (allKanji.get(i).getKanji().compareTo(allKanji.get(j).getKanji()) < 0) {
                    Kanji tmp = allKanji.get(i);
                    allKanji.set(i, allKanji.get(j));
                    allKanji.set(j, tmp);
                }
            }
        }

        for (int i = 0, j = 0; i < allKanji.size(); i++) {
            FragmentManager fragmentManager = getFragmentManager();
            Kanji kanji = allKanji.get(i);
            String reading = String.format("On: %s;\nKun: %s", kanji.getKunyomiReading(), kanji.getOnyomiReading());
            KanjiRowFragment fragment = KanjiRowFragment.newInstance(kanji.getStudyLevel().getImageId(),
                    reading, kanji.getMeaning(), kanji.getKanji(), EntityType.KANJI, false, i + 1, kanji.getNoryokuLevel());
            fragment.setAppContext(this);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.kanji_container, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
