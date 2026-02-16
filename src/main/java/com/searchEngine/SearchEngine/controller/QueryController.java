package com.searchEngine.SearchEngine.controller;


import com.searchEngine.SearchEngine.dto.QueryRequest;
import com.searchEngine.SearchEngine.entity.DocSimilarity;
import com.searchEngine.SearchEngine.entity.MyDocument;
import com.searchEngine.SearchEngine.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @PostMapping("")
    public ResponseEntity<List<DocSimilarity>> search(@RequestBody QueryRequest queryRequest){
        return new ResponseEntity(queryService.search(queryRequest), HttpStatus.OK);
    }

    @PostMapping("/crawl")
    public String runCrawler() throws IOException {
        queryService.runCrawler();
        return "Crawler Started";
    }
}
