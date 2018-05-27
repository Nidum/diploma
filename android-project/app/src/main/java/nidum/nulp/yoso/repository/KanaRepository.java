package nidum.nulp.yoso.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import nidum.nulp.yoso.entity.Kana;

public class KanaRepository {
    private static String KANA_TABLE = "kana";
    private static String READING_COLUMN = "reading";
    private static String HIRAGANA_COLUMN = "hiragana";
    private static String KATAKANA_COLUMN = "katakana";
    private static String ORDER_COLUMN = "alphabetic_order";

    private DBHelper helper;

    public KanaRepository(DBHelper helper){
        this.helper = helper;
    }

    public List<Kana> getAllKana(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + KANA_TABLE, null);

        List<Kana> kanaList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                String reading = cursor.getString(cursor.getColumnIndex(READING_COLUMN));
                String hiragana = cursor.getString(cursor.getColumnIndex(HIRAGANA_COLUMN));
                String katakana = cursor.getString(cursor.getColumnIndex(KATAKANA_COLUMN));
                int order = cursor.getInt(cursor.getColumnIndex(ORDER_COLUMN));

                kanaList.add(new Kana(reading, hiragana, katakana, order));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return kanaList;
    }
}
