package com.zpi.plagiarism_detector.server.data;

import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.server.ServerProperties;
import com.zpi.plagiarism_detector.server.websearch.GoogleSearch;
import com.zpi.plagiarism_detector.server.websearch.WebsiteAnalyze;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WebData {
    private GoogleSearch googleSearch;
    private WebsiteAnalyze websiteAnalyze;

    public WebData(GoogleSearch googleSearch, WebsiteAnalyze websiteAnalyze) {
        this.googleSearch = googleSearch;
        this.websiteAnalyze = websiteAnalyze;
    }

    public List<DocumentData> searchDocuments(Set<String> keywords, List<String> databaseLinks) {
        List<DocumentData> ret = new ArrayList<>();
        try {
            String[] linksForKeywords = getLinksForKeywords(keywords);
            String[] dbLinks = databaseLinks.toArray(new String[databaseLinks.size()]);
            ret = websiteAnalyze.analyze(linksForKeywords, dbLinks, keywords, ServerProperties.SEARCH_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private String[] getLinksForKeywords(Set<String> keywords) {
        String[] links = null;
        try {
            links = googleSearch.search(ServerProperties.NUMBER_OF_RESULTS, keywords);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }

}
