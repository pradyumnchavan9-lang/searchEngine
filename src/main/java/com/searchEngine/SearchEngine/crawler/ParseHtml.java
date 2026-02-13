package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.ParsedPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class ParseHtml {

    public ParsedPage parse(String rawHtml, String url){

        ParsedPage parsedPage = new ParsedPage();

        //Convert raw html to document
        Document doc = Jsoup.parse(rawHtml,url);

        //Remove junk
        doc.select("script,style,noscript").remove();

        //Extract Text
        String cleanText = doc.body() !=null ?doc.body().text() : "";

        //Extract Links
        Elements elements = doc.select("a[href]");

        Set<String> result = new HashSet<>();

        for(Element element : elements){
            String URL = element.attr("abs:href");

            if(!URL.isEmpty()  && (URL.startsWith("https") || URL.startsWith("http"))){
                result.add(URL);
            }
        }

        parsedPage.setTextContent(cleanText);
        parsedPage.setOutGoingLinks(result);

        return parsedPage;
    }
}
