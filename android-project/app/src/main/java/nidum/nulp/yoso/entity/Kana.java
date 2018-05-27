package nidum.nulp.yoso.entity;

public class Kana {
    private String reading;
    private String hiragana;
    private String katakana;
    private int order;

    public Kana() {
    }

    public Kana(String reading, String hiragana, String katakana, int order) {
        this.reading = reading;
        this.hiragana = hiragana;
        this.katakana = katakana;
        this.order = order;
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
}
