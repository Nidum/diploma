package nidum.nulp.yoso.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.List;

import nidum.nulp.yoso.activity.fragment.KanjiRowFragment;
import nidum.nulp.yoso.entity.Radical;
import nidum.nulp.yoso.entity.enumeration.EntityType;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.RadicalsRepository;
import nidum.nulp.yoso_project.R;

public class RadicalsListActivity extends AppCompatActivity {

    private LinearLayout radicalsHolder;
    private RadicalsRepository radicalsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radicals);
        radicalsRepository = new RadicalsRepository(new DBHelper(this));
        radicalsHolder = findViewById(R.id.radicals_container);
        initRadicals();
    }

    private void initRadicals() {
        List<Radical> allRadicals = radicalsRepository.getAll();

        for (int i = 0, j = 0; i < allRadicals.size(); i++) {
            FragmentManager fragmentManager = getFragmentManager();
            Radical radical = allRadicals.get(i);
            KanjiRowFragment fragment = KanjiRowFragment.newInstance(radical.getStudyLevel().getImageId(),
                    radical.getReading(), radical.getMeaning(), radical.getRadical(), EntityType.RADICAL, radical.isImportant(), i + 1, null);
            fragment.setAppContext(this);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(radicalsHolder.getId(), fragment);
            fragmentTransaction.commit();
        }
    }
}
