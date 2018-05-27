package nidum.nulp.yoso.kanjivg;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathParser {

    private static volatile Float coefficient;

    public static List<Path> parse(String list, float resizeCoeficient, float vertical, float horizontal) {
        coefficient = resizeCoeficient;
        final Matcher matchPathCmd = Pattern.compile("([MmLlHhVvAaQqTtCcSsZz])|([-+]?((\\d*\\.\\d+)|(\\d+))([eE][-+]?\\d+)?)").matcher(list);

        LinkedList<String> tokens = new LinkedList<>();
        while (matchPathCmd.find()) {
            tokens.addLast(matchPathCmd.group());
        }

        List<Path> paths = new ArrayList<>();
        Path path = new Path();
        char curCmd = 'Z';
        while (tokens.size() != 0) {
            String curToken = tokens.removeFirst();
            char initChar = curToken.charAt(0);
            if ((initChar >= 'A' && initChar <= 'Z') || (initChar >= 'a' && initChar <= 'z')) {
                curCmd = initChar;
            } else {
                tokens.addFirst(curToken);
            }

            switch (curCmd) {
                case 'M':
                    if (!path.isEmpty()) {
                        paths.add(path);
                        path = new Path();
                    }
                    path.moveTo(nextFloat(tokens) + horizontal, nextFloat(tokens) + vertical);
                    curCmd = 'L';
                    break;
                case 'm':
                    if (!path.isEmpty()) {
                        paths.add(path);
                        path = new Path();
                    }
                    path.rMoveTo(nextFloat(tokens), nextFloat(tokens));
                    curCmd = 'l';
                    break;
                case 'L':
                    path.lineTo(nextFloat(tokens) + horizontal, nextFloat(tokens) + vertical);
                    break;
                case 'l':
                    path.rLineTo(nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'A':
                case 'a':
                    break;
                case 'Q':
                    path.quadTo(nextFloat(tokens) + horizontal, nextFloat(tokens) + vertical,
                            nextFloat(tokens) + horizontal, nextFloat(tokens) + vertical);
                    break;
                case 'q':
                    path.rQuadTo(nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'C':
                    path.cubicTo(nextFloat(tokens) + horizontal, nextFloat(tokens) + vertical,
                            nextFloat(tokens) + horizontal, nextFloat(tokens) + vertical,
                            nextFloat(tokens) + horizontal, nextFloat(tokens) + vertical);
                    break;
                case 'c':
                    path.rCubicTo(nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens),
                            nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    break;
                default:
                    throw new RuntimeException("Invalid path element");
            }
        }
        paths.add(path);
        return paths;
    }

    static private float nextFloat(LinkedList<String> l) {
        String s = l.removeFirst();
        return Float.parseFloat(s) * coefficient;
    }
}
