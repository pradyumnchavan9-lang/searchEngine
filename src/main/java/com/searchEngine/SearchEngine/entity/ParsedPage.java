package com.searchEngine.SearchEngine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ParsedPage {

    String textContent;
    Set<String> outGoingLinks = new HashSet<>();
}
