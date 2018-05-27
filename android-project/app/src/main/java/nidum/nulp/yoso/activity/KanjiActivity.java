package nidum.nulp.yoso.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import nidum.nulp.yoso.entity.Kanji;
import nidum.nulp.yoso.repository.DBHelper;
import nidum.nulp.yoso.repository.KanaRepository;
import nidum.nulp.yoso.repository.KanjiRepository;
import nidum.nulp.yoso_project.R;

public class KanjiActivity extends AppCompatActivity {

    private KanjiRepository kanjiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji);
        kanjiRepository = new KanjiRepository(new DBHelper(this));

        setListener(R.id.layout_n1, 1);
        setListener(R.id.layout_n2, 2);
        setListener(R.id.layout_n3, 3);
        setListener(R.id.layout_n4, 4);
        setListener(R.id.layout_n5, 5);

    }

    private void setListener(int id, final int noryokuLevel){
        View layout = findViewById(id);
        final Context that = this;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, KanjiListActivity.class);
                intent.putExtra("NORYOKU_LEVEL", noryokuLevel);
                startActivity(intent);
            }
        });
    }
}
