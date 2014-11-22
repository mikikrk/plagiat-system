package com.zpi.plagiarism_detector.server.sourcecode;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.*;

public class SourceCodeTest {
    private SourceCode sc;

    @BeforeMethod
    public final void initMocks() throws IOException, ClassNotFoundException {
        sc = mock(SourceCode.class);
    }

    @Test
    public void testAdd() throws IOException {

        doThrow(new IOException()).when(sc).add(new File(""));
    }

    @Test
    public void testAdd2() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("cpp.txt");

        doThrow(new net.sourceforge.pmd.lang.ast.TokenMgrError()).when(sc).add(new File(url.getPath()));
    }

    @Test
    public void testRecognizeLanguage() {

        URL url = Thread.currentThread().getContextClassLoader().getResource("highlight.pack.js");

        doReturn("jsp").when(sc).recognizeLanguage(url.getPath());
    }

    public void testRecognizeLanguage2() {

        URL url = Thread.currentThread().getContextClassLoader().getResource("cpp.txt");

        doReturn("cpp").when(sc).recognizeLanguage(url.getPath());
    }

}
