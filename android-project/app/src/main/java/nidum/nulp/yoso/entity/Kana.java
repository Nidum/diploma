package nidum.nulp.yoso.entity;


import nidum.nulp.yoso.entity.enumeration.StudyLevel;

public class Kana {
    private String reading;
    private String hiragana;
    private String katakana;
    private int order;
    private StudyLevel hiraganaStudyLevel;
    private long hiraganaLastReviewed;
    private StudyLevel katakanaStudyLevel;
    private long katakanaLastReviewed;

    public Kana() {
    }

    public Kana(String reading, String hiragana, String katakana, int order, StudyLevel hiraganaStudyLevel,
                long hiraganaLastReviewed, StudyLevel katakanaStudyLevel, long katakanaLastReviewed) {
        this.reading = reading;
        this.hiragana = hiragana;
        this.katakana = katakana;
        this.order = order;
        this.hiraganaStudyLevel = hiraganaStudyLevel;
        this.hiraganaLastReviewed = hiraganaLastReviewed;
        this.katakanaStudyLevel = katakanaStudyLevel;
        this.katakanaLastReviewed = katakanaLastReviewed;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getHiragana() {
        return hiragana;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    public String getKatakana() {
        return katakana;
    }

    public void setKatakana(String katakana) {
        this.katakana = katakana;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public StudyLevel getHiraganaStudyLevel() {
        return hiraganaStudyLevel;
    }

    public void setHiraganaStudyLevel(StudyLevel hiraganaStudyLevel) {
        this.hiraganaStudyLevel = hiraganaStudyLevel;
    }

    public long getHiraganaLastReviewed() {
        return hiraganaLastReviewed;
    }

    public void setHiraganaLastReviewed(long hiraganaLastReviewed) {
        this.hiraganaLastReviewed = hiraganaLastReviewed;
    }

    public StudyLevel getKatakanaStudyLevel() {
        return katakanaStudyLevel;
    }

    public void setKatakanaStudyLevel(StudyLevel katakanaStudyLevel) {
        this.katakanaStudyLevel = katakanaStudyLevel;
    }

    public long getKatakanaLastReviewed() {
        return katakanaLastReviewed;
    }

    public void setKatakanaLastReviewed(long katakanaLastReviewed) {
        this.katakanaLastReviewed = katakanaLastReviewed;
    }
}
