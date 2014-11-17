package com.zpi.plagiarism_detector.server.data;

import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.commons.database.DocumentType;
import com.zpi.plagiarism_detector.server.ServerProperties;
import com.zpi.plagiarism_detector.server.database.Article;
import com.zpi.plagiarism_detector.server.database.Dao;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ServerData {
    private Dao dao;
    private FileData fileData;

    private String path;

    private String title;
    private String article;
    private Set<String> keywords;
    private Set<String> codes;

    public ServerData(Dao dao, FileData fileData) {
        this.dao = dao;
        this.fileData = fileData;
    }

    /**
     * Zapisuje dokumenty w odpowiednie miejsca w drzewie katalogów
     * @param foundDocs dokumenty do zapisania
     */
    public void saveDocuments(List<DocumentData> foundDocs) {
        for (DocumentData document : foundDocs) {
            saveDocument(document);
        }
    }

    /**
     * Zapisuje dokument w odpowiednim miejscu w drzewie katalogów
     * @param document dokument do zapisania
     */
    public void saveDocument(DocumentData document) {
        try {
            extractFields(document);
            saveArticleReferenceInDatabase();
            createArticleDirectory();
            if (article != null) {
                saveArticleText();
            }
            if(codes != null && !codes.isEmpty()) {
                saveArticleCodes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extractFields(DocumentData document) {
        title = document.getTitle();
        keywords = document.getKeywords();
        article = document.getArticle();
        codes = document.getCodes();
        path = getDirectoryPath();
    }

    private String getDirectoryPath() {
        return ServerProperties.DOCS_PATH + "/" + title;
    }

    private void saveArticleReferenceInDatabase() {
        Article newArticle = new Article(path, DocumentType.PL);
        dao.addArticle(newArticle, keywords);
    }

    private void createArticleDirectory() throws IOException {
        fileData.createDir(path);
    }

    private void saveArticleText() throws IOException {
        String articlePath = getArticlePath();
        fileData.writeToFile(articlePath, article);
    }

    private String getArticlePath() {
        return path + "/article";
    }

    private void saveArticleCodes() throws IOException {
        int i = 1;
        for (String code : codes) {
            String codePath = getCodePath(i);
            fileData.writeToFile(codePath, code);
            ++i;
        }
    }

    private String getCodePath(int i) {
        return path + "/code" + i;
    }

    public List<DocumentData> getCommonKeywordDocuments(String keywords) {
        return null;
    }

    public List<String> getLinksFromDatabase() {
        List<String> articlesLinks = dao.getArticlesLinks();
        return articlesLinks;
    }
}
