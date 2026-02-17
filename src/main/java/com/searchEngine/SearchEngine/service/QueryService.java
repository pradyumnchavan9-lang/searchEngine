package com.searchEngine.SearchEngine.service;

import com.searchEngine.SearchEngine.crawler.*;
import com.searchEngine.SearchEngine.dto.DocResponse;
import com.searchEngine.SearchEngine.dto.QueryRequest;
import com.searchEngine.SearchEngine.entity.DocSimilarity;
import com.searchEngine.SearchEngine.entity.MyDocument;
import com.searchEngine.SearchEngine.entity.Query;
import com.searchEngine.SearchEngine.mapper.DocMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private DocMapper docMapper;

    public List<DocResponse> search(QueryRequest queryRequest){
        String query = queryRequest.getQuery();
        Query queryObj = queryPreprocessing.preprocessQuery(query);
        //We need Required Docs first
        Map<String,MyDocument> docIdMap = searchEngine.getDocIdMap();
        Set<MyDocument> requiredDocs = cosineSimilarity.getRequiredDocs(queryObj,docIdMap);
        List<DocSimilarity> docSimilarity =  rankingPipeline.computeSimilaritiesForAllDocs(requiredDocs,queryObj);
        List<DocResponse> docResponseList = new ArrayList<>();
        for(DocSimilarity doc : docSimilarity){
            DocResponse docResponse = docMapper.myDocToResponse(doc);
            if(!docResponseList.contains(docResponse)) {
                docResponseList.add(docResponse);
            }
        }
        return docResponseList;
    }

    public void runCrawler() throws IOException {

        runCrawler.fillMyDocs();
    }
}
