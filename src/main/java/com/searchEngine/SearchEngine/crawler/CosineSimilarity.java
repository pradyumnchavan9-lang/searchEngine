package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;
import com.searchEngine.SearchEngine.entity.Query;

import java.util.Map;

public class CosineSimilarity {

    private MyDocument myDoc;
    private Query query;
    public CosineSimilarity(MyDocument myDoc, Query query) {
        this.myDoc = myDoc;
        this.query = query;
    }

        public double calculateCosineSimilarity() {
            Map<String,Double> docTfIdf = myDoc.getTfIdf();
            double dotProduct = 0.0;
            for(Map.Entry<String,Double> entry : query.getQueryTfIdf().entrySet()){
                double queryTfIdf = entry.getValue();
                double tokenTfIdf = 0;
                if(docTfIdf.containsKey(entry.getKey())){
                    tokenTfIdf = docTfIdf.get(entry.getKey());
                }
                dotProduct += queryTfIdf * tokenTfIdf;

            }
            double myDocVectorMag = myDoc.getTfIdfVectorMagnitude();
            double queryVectorMag = query.getQueryVectorMagnitude();

            if(myDocVectorMag == 0 || queryVectorMag == 0){
                return 0.0;
            }

            return (dotProduct)/(myDocVectorMag*queryVectorMag);
        }
}
