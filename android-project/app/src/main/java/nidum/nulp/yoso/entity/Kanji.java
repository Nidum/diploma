package nidum.nulp.yoso.entity;

import nidum.nulp.yoso.entity.enumeration.NoryokuLevel;
import nidum.nulp.yoso.entity.enumeration.StudyLevel;

public class Kanji extends Hieroglyph {
    private String kanji;
    private String onyomiReading;
    private String kunyomiReading;
    private String meaning;
    private NoryokuLevel noryokuLevel;
    private long lastReviewed;
    private StudyLevel studyLevel;

    public Kanji() {
    }

    public Kanji(String kanji, String onyomiReading, String kunyomiReading, String meaning, NoryokuLevel noryokuLevel, long lastReviewed, StudyLevel studyLevel) {
        this.kanji = kanji;
        this.onyomiReading = onyomiReading;
        this.kunyomiReading = kunyomiReading;
        this.meaning = meaning;
        this.noryokuLevel = noryokuLevel;
        this.lastReviewed = lastReviewed;
        this.studyLevel = studyLevel;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getOnyomiReading() {
        return onyomiReading;
    }

    public void setOnyomiReading(String onyomiReading) {
        this.onyomiReading = onyomiReading;
    }

    public String getKunyomiReading() {
        return kunyomiReading;
    }

    public void setKunyomiReading(String kunyomiReading) {
        this.kunyomiReading = kunyomiReading;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public NoryokuLevel getNoryokuLevel() {
        return noryokuLevel;
    }

    public void setNoryokuLevel(NoryokuLevel noryokuLevel) {
        this.noryokuLevel = noryokuLevel;
    }

    public long getLastReviewed() {
        return lastReviewed;
    }

    public void setLastReviewed(long lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }
}
