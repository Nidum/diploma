package nidum.nulp.yoso.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;

public class KanaRepository {
    private static String KANA_TABLE = "kana";
    private static String READING_COLUMN = "reading";
    private static String HIRAGANA_COLUMN = "hiragana";
    private static String KATAKANA_COLUMN = "katakana";
    private static String ORDER_COLUMN = "alphabetic_order";
    private static String LAST_REVIEWED_HIRAGANA_COLUMN = "last_reviewed_hiragana";
    private static String STUDY_LEVEL_HIRAGANA_COLUMN = "study_level_hiragana";
    private static String LAST_REVIEWED_KATAKANA_COLUMN = "last_reviewed_katakana";
    private static String STUDY_LEVEL_KATAKANA_COLUMN = "study_level_katakana";

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

                long lastReviewedHiragana = cursor.getLong(cursor.getColumnIndex(LAST_REVIEWED_HIRAGANA_COLUMN));
                int studyLevelNumberHiragana = cursor.getInt(cursor.getColumnIndex(STUDY_LEVEL_HIRAGANA_COLUMN));
                StudyLevel studyLevelHiragana = StudyLevel.values()[studyLevelNumberHiragana];

                long lastReviewedKatakana = cursor.getLong(cursor.getColumnIndex(LAST_REVIEWED_KATAKANA_COLUMN));
                int studyLevelNumberKatakana = cursor.getInt(cursor.getColumnIndex(STUDY_LEVEL_KATAKANA_COLUMN));
                StudyLevel studyLevelKatakana = StudyLevel.values()[studyLevelNumberKatakana];

                kanaList.add(new Kana(reading, hiragana, katakana, order, studyLevelHiragana,
                        lastReviewedHiragana, studyLevelKatakana, lastReviewedKatakana));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return kanaList;
    }

    public void updateKanaStudyData(Kana kana){
        String updateQuery = String.format("UPDATE %s SET %s = %d, %s = \'%s\', %s = %d, %s = \'%s\' WHERE hiragana = \'%s\'",
                KANA_TABLE,
                LAST_REVIEWED_HIRAGANA_COLUMN, kana.getHiraganaLastReviewed(),
                STUDY_LEVEL_HIRAGANA_COLUMN, kana.getHiraganaStudyLevel().ordinal(),
                LAST_REVIEWED_KATAKANA_COLUMN, kana.getKatakanaLastReviewed(),
                STUDY_LEVEL_KATAKANA_COLUMN, kana.getKatakanaStudyLevel().ordinal(),
                kana.getHiragana());

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(updateQuery, null);
        cursor.moveToFirst();
        cursor.close();
    }
}
