package com.searchEngine.SearchEngine.crawler;

import com.searchEngine.SearchEngine.entity.MyDocument;
import org.springframework.stereotype.Component;
import org.tartarus.snowball.ext.PorterStemmer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@Component
public class TextPreprocessor {

    //Stop Words to be removed
    private static final Set<String> stopWords =  new HashSet<>(Arrays.asList(
            "a","about","above","after","again","against","all","am","an","and","any","are","aren't",
            "as","at","be","because","been","before","being","below","between","both","but","by",
            "can","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't",
            "down","during","each","few","for","from","further","had","hadn't","has","hasn't",
            "have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers",
            "herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if",
            "in","into","is","isn't","it","it's","its","itself","just","me","more","most",
            "mustn't","my","myself","no","nor","not","now","of","off","on","once","only","or",
            "other","ought","our","ours","ourselves","out","over","own","same","she","she'd",
            "she'll","she's","should","shouldn't","so","some","such","than","that","that's",
            "the","their","theirs","them","themselves","then","there","there's","these","they",
            "they'd","they'll","they're","they've","this","those","through","to","too","under",
            "until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were",
            "weren't","what","what's","when","when's","where","where's","which","while","who",
            "who's","whom","why","why's","with","won't","would","wouldn't","you","you'd",
            "you'll","you're","you've","your","yours","yourself","yourselves"
    ));


    public static String getUniqueId(String url) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(url.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b)); // convert byte to hex
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,Double> calculateNormalizedTermFrequency(Map<String,Integer> termFrequency,
                                                               int totalTokens){
        //Computing normalized term Freq for each token
        Map<String,Double> normalizedTermFrequency = new HashMap<>();
        for(Map.Entry<String,Integer> entry : termFrequency.entrySet()){
            String token = entry.getKey();
            int tokenFrequency = entry.getValue();
            normalizedTermFrequency.put(token,(double)tokenFrequency/totalTokens);
        }
        return normalizedTermFrequency;
    }

    public List<String> textPreprocess(String cleanText) {

        PorterStemmer stemmer = new PorterStemmer();
        //Lower Case the text
        cleanText = cleanText.toLowerCase();

        //Remove special characters , numbers etc
        cleanText = cleanText.replaceAll("[^a-zA-Z\\s]", " ");


        //Get tokens using the whitespaces to split into tokens
        String[] tokens = cleanText.split("\\s+");

        List<String> finalTokens = new ArrayList<>();

        for (String token : tokens) {
            if (!token.isEmpty() && !stopWords.contains(token) && token.length() > 1) {
                stemmer.setCurrent(token);
                if(stemmer.stem()){
                    finalTokens.add(stemmer.getCurrent());
                }
            }
        }
        return finalTokens;
    }

    public Map<String,Integer> calculateTermFrequency(List<String> finalTokens){
        Map<String,Integer> termFrequency = new HashMap<>();
        for(String token : finalTokens){
            termFrequency.put(token,termFrequency.getOrDefault(token,0)+1);
        }
        return termFrequency;
    }


}
