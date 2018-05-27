package com.eleks.java.academy;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Emiliia Nesterovych on 5/7/2018.
 */
public class KanjiVgParsing {
    public static void main(String[] args) throws IOException {
//        Pattern pattern = Pattern.compile(
//                        "<Row .*?>[\\w\\W]+?" +
//                        "<Cell .*?><Data ss:Type=\"String\".*?>(.*?)<\\/Data>.*?<\\/Cell>[\\w\\W]+?" +
//                        "<Cell .*?><Data ss:Type=\"String\".*?>(.*?)<\\/Data>.*?<\\/Cell>[\\w\\W]+?" +
//                        "<\\/Row>");
//        Matcher matcher = pattern.matcher(readFile("N5.xml", Charset.defaultCharset()));
//
//        while (matcher.find()) {
//            System.out.println(String.format("%s %s\n", matcher.group(1), matcher.group(2)/*, matcher.group(3), matcher.group(4)*/));
//
//        }

        BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File("N1.txt"))
                        , Charset.forName("UTF-16")));
        String line;
        try {
            while (inputStream.ready()) {
                line = inputStream.readLine();
                String[] split = line.split("\t");
                String last = split[3];
                if(last.startsWith("\"")){
                    last = last.substring(1, last.length()-1);
                }
                System.out.println(String.format(
                        "insert into kanji " +
                                "(kanji, onyomi_reading, kunyomi_reading, meaning, study_level, last_reviewed, noryoku_level) " +
                                "values " +
                                "(\'%s\', \'%s\', \'%s\', \'%s\', 0, -1, 1);",
                        split[0], split[1], split[2], last));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
