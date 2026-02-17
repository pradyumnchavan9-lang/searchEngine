package com.searchEngine.SearchEngine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyDocument {

    String docId;
    String url;

    private List<String> tokens = new ArrayList<>();
    private Map<String,Integer> termFrequency = new HashMap<>();
    private Map<String,Double> normalizedTermFrequency = new HashMap<>();
    private Map<String,Double> tfIdf = new HashMap<>();
    private Double tfIdfVectorMagnitude;
    private String title;
    private String summary;


}
