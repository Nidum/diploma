package nidum.nulp.yoso.entity.enumeration;

import nidum.nulp.yoso_project.R;

// Leitner formula: Y = 2X+1
// 24 * 60 * 60 * 1000 = 86 400 000 Millis in 1 day.
public enum StudyLevel {
    NONE(R.drawable.sakura_none, 0L),
    LOW(R.drawable.sakura_low, 3 * 86400000),
    MIDDLE(R.drawable.sakura_mid, 5 * 86400000),
    FINE(R.drawable.sakura_fine, 7 * 86400000),
    MASTERED(R.drawable.sakura_mastered, 9 * 86400000);

    private int imageId;
    private long timeToNextReview;

    StudyLevel(int image, long timeToNextReview) {
        this.imageId = image;
        this.timeToNextReview = timeToNextReview;
    }

    public int getImageId() {
        return imageId;
    }

    public long getTimeToNextReview() {
        return timeToNextReview;
    }
}
