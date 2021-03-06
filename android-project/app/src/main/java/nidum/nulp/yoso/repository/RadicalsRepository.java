package nidum.nulp.yoso.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.Kanji;
import nidum.nulp.yoso.entity.Radical;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;

public class RadicalsRepository implements Repository<Radical> {
    private static String RADICALS_TABLE = "radicals";
    private static String STROKE_COLUMN = "stroke";
    private static String RADICAL_COLUMN = "radical";
    private static String MEANING_COLUMN = "meaning";
    private static String READING_COLUMN = "reading";
    private static String NOTES_COLUMN = "notes";
    private static String IMPORTANT_COLUMN = "important";
    private static String LAST_REVIEWED_COLUMN = "last_reviewed";
    private static String STUDY_LEVEL_COLUMN = "study_level";

    private DBHelper helper;

    public RadicalsRepository(DBHelper helper) {
        this.helper = helper;
    }

    @Override
    public List<Radical> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + RADICALS_TABLE, null);

        List<Radical> radicalsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String reading = cursor.getString(cursor.getColumnIndex(READING_COLUMN));
                int strokes = cursor.getInt(cursor.getColumnIndex(STROKE_COLUMN));
                String radical = cursor.getString(cursor.getColumnIndex(RADICAL_COLUMN));
                String meaning = cursor.getString(cursor.getColumnIndex(MEANING_COLUMN));
                String notes = cursor.getString(cursor.getColumnIndex(NOTES_COLUMN));
                boolean important = cursor.getInt(cursor.getColumnIndex(IMPORTANT_COLUMN)) != 0;
                long lastReviewed =  cursor.getLong(cursor.getColumnIndex(LAST_REVIEWED_COLUMN));

                int level = cursor.getInt(cursor.getColumnIndex(STUDY_LEVEL_COLUMN));
                StudyLevel studyLevel = StudyLevel.values()[level];

                radicalsList.add(new Radical(radical, meaning, reading, notes, important, strokes, lastReviewed, studyLevel));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        Collections.sort(radicalsList, new Comparator<Radical>() {
            @Override
            public int compare(Radical o1, Radical o2) {
                return o1.getStrokes() - o2.getStrokes();
            }
        });
        return radicalsList;
    }

    @Override
    public void updateStudyData(Radical radical) {
        String updateQuery = String.format("UPDATE %s SET %s = %d, %s = \'%s\' WHERE radical = \'%s\'",
                RADICALS_TABLE,
                LAST_REVIEWED_COLUMN, radical.getLastReviewed(),
                STUDY_LEVEL_COLUMN, radical.getStudyLevel().ordinal(),
                radical.getRadical());

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(updateQuery, null);
        cursor.moveToFirst();
        cursor.close();
    }


}
