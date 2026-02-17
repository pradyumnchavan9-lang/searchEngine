package com.searchEngine.SearchEngine.mapper;

import com.searchEngine.SearchEngine.dto.DocResponse;
import com.searchEngine.SearchEngine.entity.DocSimilarity;
import com.searchEngine.SearchEngine.entity.MyDocument;
import org.springframework.stereotype.Component;

@Component
public class DocMapper {

    public DocResponse myDocToResponse(DocSimilarity docSimilarity) {
        DocResponse myDocumentResponse = new DocResponse();
        myDocumentResponse.setTitle(docSimilarity.getMyDoc().getTitle());
        myDocumentResponse.setId(docSimilarity.getMyDoc().getDocId());
        myDocumentResponse.setUrl(docSimilarity.getMyDoc().getUrl());
        myDocumentResponse.setSummary(docSimilarity.getMyDoc().getSummary());
        return myDocumentResponse;
    }
}
