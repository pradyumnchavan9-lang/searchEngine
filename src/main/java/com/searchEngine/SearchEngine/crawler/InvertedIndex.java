package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;
import com.searchEngine.SearchEngine.entity.Query;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class InvertedIndex {

    private Map<String,Map<String,Double>> invertedIndex;
    private int totalDocuments ;

    public InvertedIndex(){
        this.invertedIndex = new HashMap<>();
        totalDocuments = 0;
    }

    public void addDocumentToInvertedIndex(String docId, Map<String,Double> normalizedTermFrequency){

        //totalDocs for idf calc
        totalDocuments++;
        // For each token map it to a set of all documents where the token is present with the docIds
        for (Map.Entry<String, Double> entry : normalizedTermFrequency.entrySet()) {
            invertedIndex
                    .computeIfAbsent(entry.getKey(), k -> new HashMap<>())
                    .put(docId, entry.getValue());
        }
    }

    public double calculateIDF(String token){
        Map<String,Double> docs = invertedIndex.get(token);
        if(docs == null){ return 0.0; }

        int docFreq = docs.size();
        //to see out of total docs in how many docs does the token appear
        return Math.log((double) totalDocuments / (1 + docFreq));
    }

    public void calculateTfIdfForQuery(Query query){

        Map<String,Double> tfIDF = new HashMap<>();
        for(Map.Entry<String,Double> entry : query.getQueryNormalizedTermFrequency().entrySet()){
            String token = entry.getKey();
            Double tf = entry.getValue();
            Double idf = calculateIDF(token);
            Double tfIdf = tf * idf;
            tfIDF.put(token,tfIdf);

        }

        query.setQueryTfIdf(tfIDF);

        double queryVectorMag = 0.0;

        for(double value : tfIDF.values()){
            queryVectorMag += value * value;
        }

        queryVectorMag = Math.sqrt(queryVectorMag);
        query.setQueryVectorMagnitude(queryVectorMag);

    }
    public void calculateTfIdf(MyDocument doc){

        Map<String,Double> tfIDF = new HashMap<>();
        for(Map.Entry<String,Double> entry : doc.getNormalizedTermFrequency().entrySet()){
            String token = entry.getKey();
            Double tf = entry.getValue();
            Double idf = calculateIDF(token);
            Double tfIdf = tf * idf;
            tfIDF.put(token,tfIdf);

        }

        doc.setTfIdf(tfIDF);

        double vectorMag = 0.0;
        for (double value : tfIDF.values()) {
            vectorMag += value * value;
        }

        vectorMag = Math.sqrt(vectorMag);
        doc.setTfIdfVectorMagnitude(vectorMag);
    }
}
