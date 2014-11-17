package com.zpi.plagiarism_detector.server.database;

public class ArticleLink {
    private int articleId;
    private String articleAddress;

    public ArticleLink(int articleId, String articleAddress) {
        this.articleId = articleId;
        this.articleAddress = articleAddress;
    }

    public ArticleLink() {
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleAddress() {
        return articleAddress;
    }

    public void setArticleAddress(String articleAddress) {
        this.articleAddress = articleAddress;
    }
}
