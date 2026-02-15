package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;
import com.searchEngine.SearchEngine.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component
public class CosineSimilarity {

    @Autowired
    private InvertedIndex invertedIndex;

    public Set<MyDocument> getRequiredDocs(Query query, Map<String, MyDocument> docIdMap){

        Map<String,Integer> queryTF = query.getQueryTermFrequency();
        Set<MyDocument> requiredDocs = new HashSet<>();
        for(String token : queryTF.keySet()){
            Map<String,Double> docNormalizedTermFrequency = invertedIndex.getInvertedIndex().get(token);
            if(docNormalizedTermFrequency != null){
                for (String docId : docNormalizedTermFrequency.keySet()) {
                    requiredDocs.add(docIdMap.get(docId));
                }
            }
        }
        return requiredDocs;
    }



        public Map<MyDocument,Double> calculateCosineSimilarity(Set<MyDocument> requiredDocs,Query query) {

            Map<MyDocument,Double> cosineSimilarityOfEachDoc = new HashMap<>();

            for(MyDocument requiredDoc : requiredDocs){
                Map<String,Double> docTfIdf = requiredDoc.getTfIdf();
                double dotProduct = 0.0;


                for(Map.Entry<String,Double> entry : query.getQueryTfIdf().entrySet()){
                    double queryTfIdf = entry.getValue();
                    double tokenTfIdf = 0;
                    if(docTfIdf.containsKey(entry.getKey())){
                        tokenTfIdf = docTfIdf.get(entry.getKey());
                    }
                    dotProduct += queryTfIdf * tokenTfIdf;

                }
                double myDocVectorMag = requiredDoc.getTfIdfVectorMagnitude();
                double queryVectorMag = query.getQueryVectorMagnitude();

                if(myDocVectorMag == 0 || queryVectorMag == 0){
                    cosineSimilarityOfEachDoc.put(requiredDoc,0.0);
                }else {
                    cosineSimilarityOfEachDoc.put(requiredDoc, (dotProduct) / (myDocVectorMag * queryVectorMag));
                }
            }



            return cosineSimilarityOfEachDoc;
        }
}
