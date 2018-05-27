package nidum.nulp.yoso.entity;

import nidum.nulp.yoso_project.R;

public enum StudyLevel {
    LOW(R.drawable.sakura_low),
    MIDDLE(R.drawable.sakura_mid),
    FINE(R.drawable.sakura_fine),
    MASTERED(R.drawable.sakura_mastered);

    private int imageId;

    StudyLevel(int image) {
        this.imageId = image;
    }

    public int getImageId() {
        return imageId;
    }
}
