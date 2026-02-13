package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;

import java.util.*;

public class TextPreprocessor {

    //Stop Words to be removed
    private static final Set<String> stopWords =  new HashSet<>(Arrays.asList(
            "the", "is", "at", "which", "on", "and", "a", "an", "of", "to"
    ));

    public Map<String,Double> calculateNormalizedTermFrequency(Map<String,Integer> termFrequency,
                                                               int totalTokens){
        //Computing normalized term Freq for each token
        Map<String,Double> normalizedTermFrequency = new HashMap<>();
        for(Map.Entry<String,Integer> entry : termFrequency.entrySet()){
            String token = entry.getKey();
            int tokenFrequency = entry.getValue();
            normalizedTermFrequency.put(token,(double)tokenFrequency/totalTokens);
        }
        return normalizedTermFrequency;
    }

    public List<String> textPreprocess(String cleanText) {

        //Lower Case the text
        cleanText = cleanText.toLowerCase();

        //Remove special characters , numbers etc
        cleanText = cleanText.replaceAll("[^a-zA-Z\\s]", " ");


        //Get tokens using the whitespaces to split into tokens
        String[] tokens = cleanText.split("\\s+");

        List<String> finalTokens = new ArrayList<>();

        for (String token : tokens) {
            if (!token.isEmpty() && !stopWords.contains(token) && token.length() > 1) {
                finalTokens.add(token);
            }
        }
        return finalTokens;
    }

    public Map<String,Integer> calculateTermFrequency(List<String> finalTokens){
        Map<String,Integer> termFrequency = new HashMap<>();
        for(String token : finalTokens){
            termFrequency.put(token,termFrequency.getOrDefault(token,0)+1);
        }
        return termFrequency;
    }
    
}
