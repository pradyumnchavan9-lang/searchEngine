package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryPreprocessing {


    @Autowired
    private  TextPreprocessor textPreprocessor;
    @Autowired
    private  InvertedIndex invertedIndex;



    public Query preprocessQuery(String query){
        Query finalQuery = new Query();
        finalQuery.setQueryTokens(textPreprocessor.textPreprocess(query));
        finalQuery.setQueryTermFrequency(textPreprocessor.calculateTermFrequency(finalQuery.getQueryTokens()));
        finalQuery.setQueryNormalizedTermFrequency(textPreprocessor.calculateNormalizedTermFrequency(
                finalQuery.getQueryTermFrequency(),
                finalQuery.getQueryTokens().size()
        ));
        invertedIndex.calculateTfIdfForQuery(finalQuery);


        return finalQuery;
    }
}
