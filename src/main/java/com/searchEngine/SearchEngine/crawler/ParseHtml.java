package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.ParsedPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ParseHtml {

    public ParsedPage parse(String rawHtml, String url){

        ParsedPage parsedPage = new ParsedPage();

        //Convert raw html to document
        Document doc = Jsoup.parse(rawHtml,url);

        //Remove junk
        doc.select("script,style,noscript").remove();

        //Extract Text and Summary
        doc.body();
        String cleanText = doc.body().text();

        Element meta = doc.select("meta[name = description]").first();
        String summary = "";

        if(meta != null && !meta.attr("content").isEmpty()){
            summary = meta.attr("content");
        }else{
            summary = cleanText;
        }

        int maxLength = 50;
        if(summary.length() > maxLength){
            int lastSpace = summary.lastIndexOf("",maxLength);
            summary = summary.substring(0,lastSpace) + "...";
        }


        //Extract Links
        Elements elements = doc.select("a[href]");

        //Extract title
        String title = doc.title();

        Set<String> result = new HashSet<>();

        for(Element element : elements){
            String URL = element.attr("abs:href");

            if(!URL.isEmpty()  && (URL.startsWith("https") || URL.startsWith("http"))){
                result.add(URL);
            }
        }

        parsedPage.setTextContent(cleanText);
        parsedPage.setOutGoingLinks(result);
        parsedPage.setTitle(title);
        parsedPage.setSummary(summary);

        return parsedPage;
    }
}
