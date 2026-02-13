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

    public List<String> tokens = new ArrayList<>();
    public Map<String,Integer> termFrequency = new HashMap<>();
    public Map<String,Double> normalizedTermFrequency = new HashMap<>();
    public Map<String,Double> tfIdf = new HashMap<>();
    public Double tfIdfVectorMagnitude;

}
