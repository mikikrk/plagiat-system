package com.zpi.plagiarism_detector.server.data;

import com.zpi.plagiarism_detector.commons.database.DocumentType;
import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.server.ServerProperties;
import com.zpi.plagiarism_detector.server.database.Article;
import com.zpi.plagiarism_detector.server.database.Dao;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

public class ServerData {
    private Dao dao;
    private FileData fileData;

    private String path;

    private String title;
    private String articleText;
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
            createArticleDirectory();
            if (articleText != null) {
                saveArticle();
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
        articleText = document.getArticle();
        codes = document.getCodes();
        path = getDirectoryPath();
    }

    private String getDirectoryPath() {
        return ServerProperties.DOCS_PATH + "/" + title;
    }

    private void createArticleDirectory() throws IOException {
        String dirPath = path;
        int postfix = 1;
        boolean created = false;

        do {
            try {
                fileData.createDir(dirPath);
                created = true;
            } catch (FileAlreadyExistsException e) {
                dirPath = path + postfix;
                ++postfix;
            }
        } while(created == false);

        path = dirPath;
    }

    private void saveArticle() throws IOException {
        String articlePath = getArticlePath();
        saveDocumentReferenceInDatabase(articlePath, DocumentType.TEXT);
        saveDocumentFile(articlePath, articleText);
    }

    private String getArticlePath() {
        return path + "/article";
    }

    private void saveDocumentReferenceInDatabase(String documentPath, DocumentType documentType) {
        Article newDocument = new Article(documentPath, documentType);
        dao.addArticle(newDocument, keywords);
    }

    private void saveDocumentFile(String articlePath, String articleText) throws IOException {
        fileData.writeToFile(articlePath, articleText);
    }

    private void saveArticleCodes() throws IOException {
        int i = 1;
        for (String code : codes) {
            String codePath = getCodePath(i);
            saveDocumentReferenceInDatabase(codePath, DocumentType.CODE);
            saveDocumentFile(codePath, code);
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
