package com.eleks.java.academy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
        System.out.println("Testing 1 - Send Http GET request");
        sendGet();
    }

    // HTTP GET request
    public static void sendGet() throws Exception {
        String url = "https://kanji.sljfaq.org/minidic-1.cgi?R=1&o=j";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

}
