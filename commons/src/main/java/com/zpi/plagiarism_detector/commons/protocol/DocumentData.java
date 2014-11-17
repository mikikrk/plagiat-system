package com.zpi.plagiarism_detector.commons.protocol;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DocumentData implements Serializable {
    private String title;
    private Set<String> keywords;
    private String article;
    private Set<String> codes;

    public DocumentData(String title, Set<String> keywords, String article, Set<String> codes) {
        this.title = title;
        this.keywords = keywords;
        this.article = article;
        this.codes = codes;
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public String getKeywordsJoined() {
        String keywordsJoined = StringUtils.join(keywords, " ");
        return keywordsJoined;
    }

    public String getArticle() {
        return article;
    }

    public Set<String> getCodes() {
        return codes;
    }
}
