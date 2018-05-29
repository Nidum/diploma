package nidum.nulp.yoso.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

import nidum.nulp.yoso.entity.Kana;
import nidum.nulp.yoso.entity.Kanji;
import nidum.nulp.yoso.entity.enumeration.NoryokuLevel;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;

public class KanjiRepository implements Repository<Kanji> {
    private static String KANJI_TABLE = "kanji";
    private static String KANJI_COLUMN = "kanji";
    private static String ONYOMI_READING_COLUMN = "onyomi_reading";
    private static String KUNYOMI_READING_COLUMN = "kunyomi_reading";
    private static String MEANING_COLUMN = "meaning";
    private static String NORYOKU_LEVEL_COLUMN = "noryoku_level";
    private static String LAST_REVIEWED_COLUMN = "last_reviewed";
    private static String STUDY_LEVEL_COLUMN = "study_level";

    private final DBHelper helper;
    private final NoryokuLevel noryokuLevel;

    public KanjiRepository(DBHelper helper, NoryokuLevel noryokuLevel) {
        this.helper = helper;
        this.noryokuLevel = noryokuLevel;
    }

    public List<Kanji> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        String query;
        if(noryokuLevel.equals(NoryokuLevel.ALL)){
            query = String.format("select * from %s", KANJI_TABLE);
        } else {
            query = String.format("select * from %s where %s = %d", KANJI_TABLE, NORYOKU_LEVEL_COLUMN, noryokuLevel.ordinal() + 1);
        }
        Cursor cursor = db.rawQuery(query, null);

        List<Kanji> kanjiList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String kanji = cursor.getString(cursor.getColumnIndex(KANJI_COLUMN));
                String onReading = cursor.getString(cursor.getColumnIndex(ONYOMI_READING_COLUMN));
                String kunReading = cursor.getString(cursor.getColumnIndex(KUNYOMI_READING_COLUMN));
                String meaning = cursor.getString(cursor.getColumnIndex(MEANING_COLUMN));
                int noryokuLevelNumber = cursor.getInt(cursor.getColumnIndex(NORYOKU_LEVEL_COLUMN));
                NoryokuLevel noryokuLevel = NoryokuLevel.values()[noryokuLevelNumber - 1]; // Numeration starts from 0.
                long lastReviewed = cursor.getLong(cursor.getColumnIndex(LAST_REVIEWED_COLUMN));

                int studyLevelNumber = cursor.getInt(cursor.getColumnIndex(STUDY_LEVEL_COLUMN));
                StudyLevel studyLevel = StudyLevel.values()[studyLevelNumber];

                kanjiList.add(new Kanji(kanji, onReading, kunReading, meaning, noryokuLevel, lastReviewed, studyLevel));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        Collections.sort(kanjiList, new Comparator<Kanji>() {
            @Override
            public int compare(Kanji o1, Kanji o2) {
                return o1.getKanji().compareTo(o2.getKanji());
            }
        });
        return kanjiList;
    }

    @Override
    public void updateStudyData(Kanji hieroglyph) {

    }

    public EnumMap<StudyLevel, Integer> countByLevel() {
        EnumMap<StudyLevel, Integer> result = new EnumMap<>(StudyLevel.class);

        List<Kanji> all = getAll();
        for (Kanji kanji : all) {
            StudyLevel studyLevel = kanji.getStudyLevel();
            if (result.containsKey(studyLevel)) {
                result.put(studyLevel, result.get(studyLevel) + 1);
            } else {
                result.put(studyLevel, 1);
            }
        }

        return result;
    }
}
