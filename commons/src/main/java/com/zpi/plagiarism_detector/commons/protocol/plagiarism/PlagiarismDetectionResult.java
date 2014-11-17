package com.zpi.plagiarism_detector.commons.protocol.plagiarism;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlagiarismDetectionResult implements Serializable {
    private boolean isPlagiarism;

	private List<PlagiarismResult> plagiarisms;

    public PlagiarismDetectionResult() {
        this.plagiarisms = new ArrayList<>();
        this.isPlagiarism = !plagiarisms.isEmpty();
    }

    public PlagiarismDetectionResult(List<PlagiarismResult> plagiarisms) {
        this.plagiarisms = plagiarisms;
    }

    public void addPlagiarismResult(PlagiarismResult plagiarismResult) {
        plagiarisms.add(plagiarismResult);
    }

    public boolean isPlagiarism() {
        return isPlagiarism;
    }
}
