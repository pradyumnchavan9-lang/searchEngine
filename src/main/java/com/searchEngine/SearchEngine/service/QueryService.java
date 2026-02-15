package com.searchEngine.SearchEngine.service;

import com.searchEngine.SearchEngine.crawler.*;
import com.searchEngine.SearchEngine.dto.QueryRequest;
import com.searchEngine.SearchEngine.entity.DocSimilarity;
import com.searchEngine.SearchEngine.entity.MyDocument;
import com.searchEngine.SearchEngine.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class QueryService {

    @Autowired
    private QueryPreprocessing queryPreprocessing;
    @Autowired
    private SearchEngine searchEngine;
    @Autowired
    private CosineSimilarity cosineSimilarity;
    @Autowired
    private InvertedIndex invertedIndex;
    @Autowired
    private RankingPipeline rankingPipeline;
    @Autowired
    private RunCrawler runCrawler;

    public List<DocSimilarity> search(QueryRequest queryRequest){
        String query = queryRequest.getQuery();
        Query queryObj = queryPreprocessing.preprocessQuery(query);
        //We need Required Docs first
        Map<String,MyDocument> docIdMap = searchEngine.getDocIdMap();
        Set<MyDocument> requiredDocs = cosineSimilarity.getRequiredDocs(queryObj,docIdMap);
        return rankingPipeline.computeSimilaritiesForAllDocs(requiredDocs,queryObj);
    }

    public void runCrawler() throws IOException {

        runCrawler.fillMyDocs();
    }
}
