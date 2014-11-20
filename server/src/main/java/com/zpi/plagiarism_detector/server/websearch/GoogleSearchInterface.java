package com.zpi.plagiarism_detector.server.websearch;

import java.util.Set;

public interface GoogleSearchInterface {
    String[] search(int numberOfResults, Set<String> keywords);
}
