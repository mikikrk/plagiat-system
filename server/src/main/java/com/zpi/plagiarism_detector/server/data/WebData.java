package com.zpi.plagiarism_detector.server.data;

import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.server.ServerProperties;
import com.zpi.plagiarism_detector.server.websearch.GoogleSearch;
import com.zpi.plagiarism_detector.server.websearch.WebsiteAnalyze;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class WebData {
    private GoogleSearch googleSearch;
    private WebsiteAnalyze websiteAnalyze;

    public WebData(GoogleSearch googleSearch, WebsiteAnalyze websiteAnalyze) {
        this.googleSearch = googleSearch;
        this.websiteAnalyze = websiteAnalyze;
    }

    public List<DocumentData> searchDocuments(String keywords, List<String> databaseLinks) {
        try {
            String[] linksForKeywords = getLinksForKeywords(keywords);
            String[] dbLinks = databaseLinks.toArray(new String[databaseLinks.size()]);
            String lastFileName = websiteAnalyze.analyze(linksForKeywords, dbLinks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] getLinksForKeywords(String keywords) {
        String[] links = null;
        try {
            links = googleSearch.search(ServerProperties.NUMBER_OF_RESULTS, keywords);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }

}
