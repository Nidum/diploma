package nidum.nulp.yoso.entity.enumeration;

import nidum.nulp.yoso_project.R;

public enum NoryokuLevel {
    N1(R.drawable.lotus_n1),
    N2(R.drawable.lotus_n2),
    N3(R.drawable.lotus_n3),
    N4(R.drawable.lotus_n4),
    N5(R.drawable.lotus_n5);

    private int imgId;

    NoryokuLevel(int imgId) {
        this.imgId = imgId;
    }

    public int getImgId() {
        return imgId;
    }
}
