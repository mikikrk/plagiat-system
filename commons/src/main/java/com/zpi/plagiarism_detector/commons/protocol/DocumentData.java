package com.zpi.plagiarism_detector.commons.protocol;

import java.io.Serializable;
import java.util.List;

public class DocumentData implements Serializable {
    private String title;
    private List<String> keywords;
    private String article;
    private List<String> codes;
}
