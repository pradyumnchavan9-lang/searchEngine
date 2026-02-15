package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Data
public class SearchEngine {

    // Stores metadata
    private Map<String,MyDocument> docIdMap;
    // Stores inverted index
    private Map<String, List<String>> invertedIndex ;

    public SearchEngine(){
        docIdMap = new HashMap<>();
        invertedIndex = new HashMap<>();
    }

    public void addMyDocument(String docId, MyDocument myDocument) {

        docIdMap.put(docId, myDocument);
    }

}
