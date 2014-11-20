package com.zpi.plagiarism_detector.server.detector;

import com.zpi.plagiarism_detector.commons.protocol.DocumentData;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismDetectionResult;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;
import com.zpi.plagiarism_detector.server.data.ServerData;
import com.zpi.plagiarism_detector.server.data.WebData;

import java.util.List;
import java.util.Set;

public class PlagiarismDetector {
    private ServerData serverData;
    private WebData webData;
    private ComparingAlgorithm comparingAlgorithm;

    private DocumentData analyzedDocument;
    private Set<String> keywords;

    public PlagiarismDetector(ServerData serverData, WebData webData, ComparingAlgorithm comparingAlgorithm) {
        this.serverData = serverData;
        this.webData = webData;
        this.comparingAlgorithm = comparingAlgorithm;
    }

    public PlagiarismDetectionResult checkForPlagiarism(DocumentData document) {
        this.analyzedDocument = document;
        this.keywords = document.getKeywords();
        saveDocument();
        List<DocumentData> matchingDocs = getSimilarDocuments();
        List<PlagiarismResult> plagiarisms = determinePlagiarism(matchingDocs);

        PlagiarismDetectionResult result = new PlagiarismDetectionResult(plagiarisms);
        return result;
    }

    private void saveDocument() {
        serverData.saveDocument(analyzedDocument);
    }

    private List<DocumentData> getSimilarDocuments() {
        return serverData.getCommonKeywordDocuments(keywords);
    }

    private List<PlagiarismResult> determinePlagiarism(List<DocumentData> matchingDocs) {
        return comparingAlgorithm.determinePlagiarism(analyzedDocument, matchingDocs);
    }

    private void findSimilarDocumentsInWeb() {
        List<String> databaseLinks = serverData.getLinksFromDatabase();
        List<DocumentData> foundDocs = webData.searchDocuments(keywords, databaseLinks);
        serverData.saveDocuments(foundDocs);
    }

}
