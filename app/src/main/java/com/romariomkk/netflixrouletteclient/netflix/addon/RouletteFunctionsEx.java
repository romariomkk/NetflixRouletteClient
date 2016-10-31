package com.romariomkk.netflixrouletteclient.netflix.addon;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by romariomkk on 21.10.2016.
 */
public class RouletteFunctionsEx {

    static URL urlAddress;

    public static String readAll(Reader rd) throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        int result;
        while ((result = rd.read()) != -1) {
            strBuilder.append((char) result);
        }
        return strBuilder.toString();
    }

    public static String readJsonFromUrl(String url)  {
        String jsonText = null;
        InputStream inStream = null;

        try {
            urlAddress = new URL(url);
            inStream = urlAddress.openStream();
        }catch (FileNotFoundException e){
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            Log.e("INFOerr", "Error occurred while handling URL of request");
        }

        if (inStream != null) {
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(inStream, Charset.forName("UTF-8")))) {
                jsonText = readAll(rd);
            }catch (IOException exc){
                Log.e("INFOerr", "Error occurred while extracting info from Netflix Roulette");
            }
        }

        return jsonText;
    }
}