package com.searchEngine.SearchEngine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocSimilarity {

    MyDocument myDoc;
    Double similarity;

}
