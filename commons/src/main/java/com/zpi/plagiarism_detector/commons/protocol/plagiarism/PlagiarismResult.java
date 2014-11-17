package com.zpi.plagiarism_detector.commons.protocol.plagiarism;

import com.zpi.plagiarism_detector.commons.database.DocumentType;

import java.util.Map;

public class PlagiarismResult {
    private String newDocument;        //to co użytkownik przesłał
    private String existingDocument;   //to do czego jest porównywany dokument użytkownika

    private Map<PlagiarismFragment, PlagiarismFragment> plagiarisedFragments;
    private DocumentType type;

    public PlagiarismResult() {

    }

    public PlagiarismResult(String newDocument,
                            String existingDocument,
                            Map<PlagiarismFragment, PlagiarismFragment> plagiarisedFragments,
                            DocumentType type) {

        this.newDocument = newDocument;
        this.existingDocument = existingDocument;
        this.plagiarisedFragments = plagiarisedFragments;
        this.type = type;
    }

    public String getNewDocument() {
        return newDocument;
    }

    public void setNewDocument(String newDocument) {
        this.newDocument = newDocument;
    }

    public String getExistingDocument() {
        return existingDocument;
    }

    public void setExistingDocument(String existingDocument) {
        this.existingDocument = existingDocument;
    }

    public Map<PlagiarismFragment, PlagiarismFragment> getPlagiarisedFragments() {
        return plagiarisedFragments;
    }

    public void setPlagiarisedFragments(
            Map<PlagiarismFragment, PlagiarismFragment> plagiarisedFragments) {
        this.plagiarisedFragments = plagiarisedFragments;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }
}
