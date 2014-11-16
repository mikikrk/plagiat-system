package com.zpi.plagiarism_detector.commons.protocol;

import java.io.Serializable;
import java.util.List;

public class PlagiarismDetectionResult implements Serializable {
	private List<PlagiarismResult> plagiarisms;
}
