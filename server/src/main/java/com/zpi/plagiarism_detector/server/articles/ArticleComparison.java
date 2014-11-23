package com.zpi.plagiarism_detector.server.articles;

import java.io.IOException;

import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;

public class ArticleComparison {
    private CompareEngine compareEngine;


    public ArticleComparison(CompareEngine compareEngine) {
        this.compareEngine = compareEngine;
    }

    public PlagiarismResult compare(String patternPath, String textPath) {
        try {
            return compareEngine.compare(patternPath, textPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
