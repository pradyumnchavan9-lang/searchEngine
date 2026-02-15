package com.searchEngine.SearchEngine;

import com.searchEngine.SearchEngine.crawler.FetchHtml;
import com.searchEngine.SearchEngine.crawler.RunCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SearchEngineApplication {




	public static void main(String[] args) throws IOException {
        SpringApplication.run(SearchEngineApplication.class, args);


    }
}
