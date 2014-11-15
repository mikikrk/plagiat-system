package com.zpi.plagiarism_detector.server.detector;

import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.commons.protocol.PlagiarismDetectionResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PlagiarismDetectorTest {
    private PlagiarismDetector plagiarismDetector;

    @BeforeMethod
    public void init() {
        plagiarismDetector = new PlagiarismDetector();
    }

    @Test
    public void test1() {
        // given
        DocumentData document = null;

        // when
        PlagiarismDetectionResult result = plagiarismDetector.checkForPlagiarism(document);

        // then

    }
}
