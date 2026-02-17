package com.searchEngine.SearchEngine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ParsedPage {

    private String textContent;
    private Set<String> outGoingLinks = new HashSet<>();
    private String title;
    private String summary;
}
