package nidum.nulp.yoso.entity;
import java.util.Date;

public class Radical {
    private String radical;
    private String meaning;
    private String reading;
    private String notes;
    private boolean important;
    private int strokes;
    private long lastReviewed;
    private StudyLevel studyLevel;

    public String getRadical() {
        return radical;
    }

    public Radical(String radical, String meaning, String reading, String notes, boolean important, int strokes, long lastReviewed, StudyLevel studyLevel) {
        this.radical = radical;
        this.meaning = meaning;
        this.reading = reading;
        this.notes = notes;
        this.important = important;
        this.strokes = strokes;
        this.lastReviewed = lastReviewed;
        this.studyLevel = studyLevel;
    }

    public void setRadical(String radical) {
        this.radical = radical;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
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

    public int getStrokes() {
        return strokes;
    }

    public void setStrokes(int strokes) {
        this.strokes = strokes;
    }
}
