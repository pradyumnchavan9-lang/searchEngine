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
public class Query {

    List<String> queryTokens = new ArrayList<>();
    Map<String,Integer> queryTermFrequency = new HashMap<>();
    Map<String,Double> queryNormalizedTermFrequency = new HashMap<>();
    Map<String,Double> queryTfIdf = new HashMap<>();
    Double queryVectorMagnitude;
}
