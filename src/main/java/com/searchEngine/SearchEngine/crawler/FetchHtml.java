package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;


@Component
public class FetchHtml {

    public String fetcher(String urlString) throws IOException {


        StringBuilder html = new StringBuilder();

        //1:Accept a String as the parameter
        //2:Convert to URL object
        URL url = new URL(urlString);

        //3:Open Http Connection using the url object
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //4:Set Request Methods and Timeouts
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);

        //5:Check Response Code
        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            //6:Read Response Body
            InputStream inputStream = connection.getInputStream();

            //7:This is in the form of bytes we want in Sting
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while( (line = reader.readLine()) != null){
                html.append(line);
            }

        }
        connection.disconnect();
        return html.toString();
    }
}
