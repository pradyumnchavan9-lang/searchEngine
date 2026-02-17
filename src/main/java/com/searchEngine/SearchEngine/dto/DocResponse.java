package com.searchEngine.SearchEngine.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocResponse {

    private String url;
    private String id;
    private String title;
    private String summary;
}
