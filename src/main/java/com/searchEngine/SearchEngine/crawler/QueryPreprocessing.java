package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.Query;

public class QueryPreprocessing {

    private final TextPreprocessor textPreprocessor;
    private final InvertedIndex invertedIndex;

    public QueryPreprocessing(TextPreprocessor textPreprocessor, InvertedIndex invertedIndex) {
        this.textPreprocessor = textPreprocessor;
        this.invertedIndex = invertedIndex;
    }

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
