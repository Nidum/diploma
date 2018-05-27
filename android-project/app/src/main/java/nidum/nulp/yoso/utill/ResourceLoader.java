package nidum.nulp.yoso.utill;

import android.content.Context;
import android.graphics.Typeface;

public class ResourceLoader {
    public static Typeface KANJI_FONT;

    public static void loadResources(Context appContext){
        KANJI_FONT = Typeface.createFromAsset(appContext.getAssets(),  "fonts/Radicals Font.ttf");
    }
}
