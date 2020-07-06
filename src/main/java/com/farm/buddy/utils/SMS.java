package com.farm.buddy.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMS {
    private String key;

    public SMS(String key) {
        this.key = key;
    }

    /**
     * @param number
     * @param message
     * @return
     */
    public String send(String number, String message) {
        String sResult = "";
        try {
            // Construct data
            String apiKey = "apikey=" + URLEncoder.encode(key, "UTF-8");
            message = "&message=" + URLEncoder.encode(message, "UTF-8");
            String sender = "&sender=" + URLEncoder.encode("TXTLCL", "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode(number, "UTF-8");

            // Send data
            String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                // Process line...
                sResult = sResult + line + " ";
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sResult;
    }
}
