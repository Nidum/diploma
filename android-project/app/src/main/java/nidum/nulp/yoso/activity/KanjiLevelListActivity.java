package nidum.nulp.yoso.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import nidum.nulp.yoso.entity.Kanji;
import nidum.nulp.yoso.entity.enumeration.NoryokuLevel;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.KanaRepository;
import nidum.nulp.yoso.repository.KanjiRepository;
import nidum.nulp.yoso_project.R;

import static nidum.nulp.yoso.entity.enumeration.NoryokuLevel.N1;
import static nidum.nulp.yoso.entity.enumeration.NoryokuLevel.N2;
import static nidum.nulp.yoso.entity.enumeration.NoryokuLevel.N3;
import static nidum.nulp.yoso.entity.enumeration.NoryokuLevel.N4;
import static nidum.nulp.yoso.entity.enumeration.NoryokuLevel.N5;
import static nidum.nulp.yoso.utill.IntentHolder.ARG_NORYOKU_LEVEL;

public class KanjiLevelListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji);

        setListener(R.id.layout_n1, N1);
        setListener(R.id.layout_n2, N2);
        setListener(R.id.layout_n3, N3);
        setListener(R.id.layout_n4, N4);
        setListener(R.id.layout_n5, N5);
    }

    private void setListener(int id, final NoryokuLevel noryokuLevel){
        View layout = findViewById(id);
        final Context that = this;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, KanjiListActivity.class);
                intent.putExtra(ARG_NORYOKU_LEVEL, noryokuLevel.name());
                startActivity(intent);
            }
        });
    }
}
