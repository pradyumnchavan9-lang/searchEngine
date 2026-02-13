package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SearchEngine {

    // Stores metadata
    private Map<String, MyDocument> docStore = new HashMap<>();

    // Stores inverted index
    private Map<String, List<String>> invertedIndex = new HashMap<>();

    public void addMyDocument(String url,List<String> tokens) {

        String docId = UUID.randomUUID().toString();

        MyDocument doc = new MyDocument();
        docStore.put(docId, doc);
    }
}
