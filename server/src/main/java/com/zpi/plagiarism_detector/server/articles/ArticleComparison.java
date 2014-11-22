package com.zpi.plagiarism_detector.server.articles;

import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ArticleComparison {
    private CompareEngine compareEngine;


    public ArticleComparison(CompareEngine compareEngine) {
        this.compareEngine = compareEngine;
    }

    public List<PlagiarismResult> compare(String patternPath, String textPath) {
        try {
            return compareEngine.compare(patternPath, textPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
