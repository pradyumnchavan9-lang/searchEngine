package com.searchEngine.SearchEngine;

import com.searchEngine.SearchEngine.crawler.FetchHtml;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SearchEngineApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SearchEngineApplication.class, args);

        FetchHtml fetch = new FetchHtml();
        List<String> urls = new ArrayList<>();
        urls.add("https://www.example.com");
        urls.add("https://www.wikipedia.org");
        urls.add("https://www.geeksforgeeks.org");
        urls.add("https://www.stackoverflow.com");
        urls.add("https://www.oracle.com");
        for(int i = 0;i<urls.size();i++){
            System.out.println(fetch.fetchHtml(urls.get(i)));
        }
	}


}
