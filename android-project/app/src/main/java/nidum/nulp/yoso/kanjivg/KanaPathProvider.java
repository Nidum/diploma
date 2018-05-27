package nidum.nulp.yoso.kanjivg;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Path;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class KanaPathProvider {

    private Context context;

    public KanaPathProvider(Context context){
        this.context = context;
    }

    public List<Path> buildPaths(char character, int width) throws IOException, XmlPullParserException {
        StringBuilder fileName = new StringBuilder(Integer.toHexString(character));
        while(fileName.length() < 5){
            fileName.insert(0, '0');
        }
        fileName.insert(0, "kanji");

        int kanaID = context.getResources().getIdentifier(fileName.toString(), "xml", context.getPackageName());
        XmlResourceParser xml = context.getResources().getXml(kanaID);

        return parseFile(xml, width);
    }

    private List<Path> parseFile(XmlResourceParser xml, int width) throws XmlPullParserException, IOException {
        xml.next();
        int eventType = xml.getEventType();
        StringBuilder builder = new StringBuilder();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            eventType = xml.getEventType();
            if(xml.getName()!= null && xml.getName().equals("path")){
                String d = xml.getAttributeValue(null, "d");
                if(d != null) {
                    builder.append(d);
                }
            }
            xml.next();
        }

        return PathParser.parse(builder.toString(), getResizeCoeficient(width), 100.0f, 0.0f);
    }

    private float getResizeCoeficient(int screenWidth){
        return 7.0f;
    }
}
