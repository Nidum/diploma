package nidum.nulp.yoso.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import nidum.nulp.yoso.entity.Kanji;
import nidum.nulp.yoso.entity.enumeration.NoryokuLevel;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;

public class KanjiRepository {
    private static String KANJI_TABLE = "kanji";
    private static String KANJI_COLUMN = "kanji";
    private static String ONYOMI_READING_COLUMN = "onyomi_reading";
    private static String KUNYOMI_READING_COLUMN = "kunyomi_reading";
    private static String MEANING_COLUMN = "meaning";
    private static String NORYOKU_LEVEL_COLUMN = "noryoku_level";
    private static String LAST_REVIEWED_COLUMN = "last_reviewed";
    private static String STUDY_LEVEL_COLUMN = "study_level";

    private DBHelper helper;

    public KanjiRepository(DBHelper helper) {
        this.helper = helper;
    }

    public List<Kanji> getAllKanjiByLevel(int level) {
        if (level > 5 || level < 1) {
            throw new RuntimeException("Please, select a valid noryoku shiken level.");
        }
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("select * from %s where %s = %d", KANJI_TABLE, NORYOKU_LEVEL_COLUMN, level), null);

        List<Kanji> kanjiList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String kanji = cursor.getString(cursor.getColumnIndex(KANJI_COLUMN));
                String onReading = cursor.getString(cursor.getColumnIndex(ONYOMI_READING_COLUMN));
                String kunReading = cursor.getString(cursor.getColumnIndex(KUNYOMI_READING_COLUMN));
                String meaning = cursor.getString(cursor.getColumnIndex(MEANING_COLUMN));
                int noryokuLevelNumber = cursor.getInt(cursor.getColumnIndex(NORYOKU_LEVEL_COLUMN));
                NoryokuLevel noryokuLevel = NoryokuLevel.values()[noryokuLevelNumber];
                long lastReviewed = cursor.getLong(cursor.getColumnIndex(LAST_REVIEWED_COLUMN));

                int studyLevelNumber = cursor.getInt(cursor.getColumnIndex(STUDY_LEVEL_COLUMN));
                StudyLevel studyLevel = StudyLevel.values()[studyLevelNumber];

                kanjiList.add(new Kanji(kanji, onReading, kunReading, meaning, noryokuLevel, lastReviewed, studyLevel));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return kanjiList;
    }
}
