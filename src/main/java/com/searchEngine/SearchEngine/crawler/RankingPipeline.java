package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.DocSimilarity;
import com.searchEngine.SearchEngine.entity.MyDocument;
import com.searchEngine.SearchEngine.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RankingPipeline {


    @Autowired
    private CosineSimilarity cosineSimilarity;


    public List<DocSimilarity> computeSimilaritiesForAllDocs(Set<MyDocument> requiredDocs, Query query){

        PriorityQueue<DocSimilarity> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a.getSimilarity()));



            Map<MyDocument,Double> cosineSimilarityOfEachDoc = cosineSimilarity.calculateCosineSimilarity(requiredDocs,query);
            for(Map.Entry<MyDocument,Double> entry : cosineSimilarityOfEachDoc.entrySet()) {
                if (pq.size() < 10) {
                    DocSimilarity docSimilarity = new DocSimilarity();
                    docSimilarity.setSimilarity(entry.getValue());
                    docSimilarity.setMyDoc(entry.getKey());
                    pq.add(docSimilarity);
                    pq.offer(docSimilarity);
                } else {
                    if (pq.peek().getSimilarity() < entry.getValue()) {
                        pq.poll();
                        DocSimilarity docSimilarity = new DocSimilarity();
                        docSimilarity.setSimilarity(entry.getValue());
                        docSimilarity.setMyDoc(entry.getKey());
                        pq.add(docSimilarity);
                        pq.offer(docSimilarity);
                    }
                }

            }

        List<DocSimilarity> similarities = new ArrayList<>();
        while(!pq.isEmpty()){
            similarities.add(pq.poll());
        }

        Collections.reverse(similarities);
        return similarities;
    }
}
