package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;
import com.searchEngine.SearchEngine.entity.ParsedPage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class RunCrawler {

    @Autowired
    private FetchHtml fetchHtml;
    @Autowired
    private ParseHtml parseHtml;
    @Autowired
    private TextPreprocessor textPreprocessor;
    @Autowired
    private SearchEngine searchEngine;
    @Autowired
    private InvertedIndex invertedIndex;


    public void runCrawlerSafely() {
        try {
            fillMyDocs();
        } catch (Exception e) {
            System.err.println("Crawler encountered an error at startup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void fillMyDocs() throws IOException {


        List<String> urls = new ArrayList<>(Arrays.asList(
                "//news.ycombinator.com/",
                "https://www.wikipedia.org/",
                "https://github.com/trending",
                "https://www.bbc.com/news",
                "https://stackoverflow.com/"
        ));

        List<MyDocument> myDocs = new ArrayList<>();

        for (String url : urls) {
            Queue<String> q = new ArrayDeque<>();
            q.offer(url);
            int seed = 0;
            while (!q.isEmpty() && seed < 2) {
                int numLevel = q.size();
                for (int i = 0; i < numLevel; i++) {
                    String currUrl = q.poll();
                    String fetchHtmlOfUrl = fetchHtml.fetcher(currUrl);
                    ParsedPage parsedPage = parseHtml.parse(fetchHtmlOfUrl, currUrl);

                    // Crawler fetching more urls
                    Set<String> newUrls = parsedPage.getOutGoingLinks();
                    for (String newUrl : newUrls) {
                        q.offer(newUrl);
                    }

                    // Crawlers work is done now to textPreprocessing for creating MyDocs
                    String urlId = textPreprocessor.getUniqueId(currUrl);
                    List<String> finalTokens = textPreprocessor.textPreprocess(parsedPage.getTextContent());
                    Map<String, Integer> termFrequency = textPreprocessor.calculateTermFrequency(finalTokens);
                    Map<String, Double> normalizedTermFrequency = textPreprocessor.calculateNormalizedTermFrequency(
                            termFrequency, finalTokens.size()
                    );

                    // Now we add id,url,tokens,termFrequency,normalizedFrequency to our doc
                    MyDocument myDocument = new MyDocument();
                    myDocument.setUrl(urlId);
                    myDocument.setUrl(currUrl);
                    myDocument.setTokens(finalTokens);
                    myDocument.setTermFrequency(termFrequency);
                    myDocument.setNormalizedTermFrequency(normalizedTermFrequency);

                    invertedIndex.addDocumentToInvertedIndex(urlId, normalizedTermFrequency);

                    // Now we want to add tfIdf and vectorMagOfTfIdf to our doc
                    invertedIndex.calculateTfIdf(myDocument);

                    // Now map the docId with its respective Document
                    searchEngine.addMyDocument(urlId, myDocument);

                    // Now add doc to List of docs
                    myDocs.add(myDocument);
                }
                seed++;
            }
        }
        System.out.println("Finished");
    }
}
