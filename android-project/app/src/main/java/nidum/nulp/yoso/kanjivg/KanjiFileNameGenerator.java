package nidum.nulp.yoso.kanjivg;

public class KanjiFileNameGenerator {
    public static String getFileName(char hieroglyph){
        StringBuilder fileName = new StringBuilder(Integer.toHexString(hieroglyph));
        while(fileName.length() < 5){
            fileName.insert(0, '0');
        }
        fileName.insert(0, "kanji");
        return fileName.toString();
    }
}
