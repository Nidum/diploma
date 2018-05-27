package nidum.nulp.yoso.kanjivg;

import android.graphics.Path;
import android.graphics.PathMeasure;

import java.util.List;

public class DurationGenerator {
    public static float getDuration(List<Path> paths, float coefficient) {
        float length = 0;
        PathMeasure measure = new PathMeasure();
        for (Path p : paths) {
            measure.setPath(p, false);
            length += measure.getLength();

        }
        return length * coefficient + paths.size() * coefficient * 2;
    }
}
