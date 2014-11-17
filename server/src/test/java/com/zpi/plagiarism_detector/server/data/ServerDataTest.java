package com.zpi.plagiarism_detector.server.data;

import com.zpi.plagiarism_detector.commons.database.DocumentType;
import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.server.ServerProperties;
import com.zpi.plagiarism_detector.server.database.Article;
import com.zpi.plagiarism_detector.server.database.Dao;
import com.zpi.plagiarism_detector.server.database.Type;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class ServerDataTest extends PowerMockTestCase {
    private ServerData serverData;
    private Dao dao;
    private FileData fileData;
    private String articleText;
    private String title;
    private Set<String> codes;
    private Set<String> keywords;
    private DocumentData document;

    @BeforeMethod
    public void init() {
        this.dao = mock(Dao.class);
        this.fileData = mock(FileData.class);
        this.serverData = new ServerData(dao, fileData);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void saveDocument_nullArgExceptionTest() {
        // given
        DocumentData document = null;


        // when
        serverData.saveDocument(document);
    }

    @Test
    public void saveDocument_NonNullDocWithoutCodesSuccessTest() throws IOException {
        // given
        keywords = new HashSet<>();
        keywords.add("keyword");
        codes = new HashSet<>();

        title = "title";
        articleText = "article";
        DocumentData document = new DocumentData(title, keywords, articleText, codes);

        // when
        serverData.saveDocument(document);

        // then
        String expectedDirPath = ServerProperties.DOCS_PATH + "/" + title;
        String expectedArtPath = expectedDirPath + "/article";

        verify(dao).addArticle(any(Article.class), eq(keywords));
        verify(fileData).createDir(expectedDirPath);
        verify(fileData).writeToFile(expectedArtPath, articleText);
    }

    @Test
    public void saveDocument_withDocumentAndCodesSuccessTest() throws IOException {
        // given
        keywords = new LinkedHashSet<>();
        keywords.add("keyword");
        codes = new LinkedHashSet<>();
        codes.add("a");
        codes.add("b");
        codes.add("c");

        title = "title";
        articleText = "article";
        DocumentData document = new DocumentData(title, keywords, articleText, codes);

        // when
        serverData.saveDocument(document);

        // then
        String expectedDirPath = ServerProperties.DOCS_PATH + "/" + title;
        String expectedArtPath = expectedDirPath + "/article";
        String expectedCodeAPath = expectedDirPath + "/code1";
        String expectedCodeBPath = expectedDirPath + "/code2";
        String expectedCodeCPath = expectedDirPath + "/code3";

        verify(dao).addArticle(any(Article.class), eq(keywords));
        verify(fileData).createDir(expectedDirPath);
        verify(fileData).writeToFile(expectedArtPath, articleText);
        verify(fileData).writeToFile(expectedCodeAPath, "a");
        verify(fileData).writeToFile(expectedCodeBPath, "b");
        verify(fileData).writeToFile(expectedCodeCPath, "c");
    }

    @Test
    public void saveDocument_withNullArticleAndCodesSuccessTest() throws IOException {
        // given
        keywords = new LinkedHashSet<>();
        keywords.add("keyword");
        codes = new LinkedHashSet<>();
        codes.add("a");
        codes.add("b");
        codes.add("c");

        title = "title";
        articleText = null;
        document = new DocumentData(title, keywords, articleText, codes);

        // when
        serverData.saveDocument(document);

        // then
        String expectedDirPath = ServerProperties.DOCS_PATH + "/" + title;
        String unexpectedArtPath = expectedDirPath + "/article";
        String expectedCodeAPath = expectedDirPath + "/code1";
        String expectedCodeBPath = expectedDirPath + "/code2";
        String expectedCodeCPath = expectedDirPath + "/code3";

        verify(dao).addArticle(any(Article.class), eq(keywords));
        verify(fileData).createDir(expectedDirPath);
        verify(fileData, times(0)).writeToFile(unexpectedArtPath, articleText);

        verify(fileData).writeToFile(expectedCodeAPath, "a");
        verify(fileData).writeToFile(expectedCodeBPath, "b");
        verify(fileData).writeToFile(expectedCodeCPath, "c");
    }
}
