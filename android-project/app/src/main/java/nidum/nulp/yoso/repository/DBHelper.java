package nidum.nulp.yoso.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import nidum.nulp.yoso_project.R;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    public DBHelper(Context context) {
        super(context, "yoso_db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        handleKanaInsert(db);

    }

    private void handleKanaInsert(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE kana ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "reading TEXT,"
                + "hiragana TEXT,"
                + "katakana TEXT,"
                + "alphabetic_order INTEGER,"
                + "last_reviewed_hiragana INTEGER,"
                + "study_level_hiragana INTEGER, "
                + "last_reviewed_katakana INTEGER,"
                + "study_level_katakana INTEGER);");

        db.execSQL("CREATE TABLE radicals ("
                +" id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +" stroke INTEGER,"
                +" radical TEXT,"
                +" meaning TEXT,"
                +" reading TEXT,"
                +" notes TEXT,"
                +" important INTEGER,"
                +" last_reviewed INTEGER,"
                +" study_level INTEGER );");

        db.execSQL("CREATE TABLE kanji ("
                +" id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +" kanji TEXT,"
                +" onyomi_reading TEXT,"
                +" kunyomi_reading TEXT,"
                +" meaning TEXT,"
                +" noryoku_level INTEGER,"
                +" last_reviewed INTEGER,"
                +" study_level INTEGER );");

        insertData(R.raw.kana, db);
        insertData(R.raw.radicals, db);
        insertData(R.raw.kanji, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // To do in future versions.
    }

    private void insertData(int resourceID, SQLiteDatabase db){
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceID)));
        try {
            while (inputStream.ready()) {
                String line = inputStream.readLine();
                db.execSQL(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

