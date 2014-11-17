package com.zpi.plagiarism_detector.server.detector;

import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismDetectionResult;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;
import com.zpi.plagiarism_detector.server.data.ServerData;
import com.zpi.plagiarism_detector.server.data.WebData;

import java.util.List;

public class PlagiarismDetector {
    private ServerData serverData;
    private WebData webData;
    private ComparingAlgorithm comparingAlgorithm;

    public PlagiarismDetector(ServerData serverData, WebData webData, ComparingAlgorithm comparingAlgorithm) {
        this.serverData = serverData;
        this.webData = webData;
        this.comparingAlgorithm = comparingAlgorithm;
    }

    public PlagiarismDetectionResult checkForPlagiarism(DocumentData document) {

        String title = document.getTitle();
        String keywords = document.getKeywordsJoined();

        serverData.saveDocument(document);
        List<String> databaseLinks = serverData.getLinksFromDatabase();
        List<DocumentData> foundDocs = webData.searchDocuments(keywords, databaseLinks);
        serverData.saveDocuments(foundDocs);

        List<DocumentData> matchingDocs = serverData.getCommonKeywordDocuments(keywords);
        List<PlagiarismResult> plagiarisms = comparingAlgorithm.determinePlagiarism(document, matchingDocs);

        PlagiarismDetectionResult result = new PlagiarismDetectionResult(plagiarisms);
        return result;
    }

}
