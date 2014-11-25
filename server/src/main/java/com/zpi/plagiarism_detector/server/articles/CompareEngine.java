package com.zpi.plagiarism_detector.server.articles;



import java.io.IOException;

import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;



public class CompareEngine {

    private String pattern = "";
    private String text = "";
    private String[] patternTab;
    private String[] textTab;
    private PlagiarismResult comparisonResult;
    private FileLoader fL;
    private TextProcessing tP = new TextProcessing();

    public CompareEngine() {
    }

    /**
     * Dzielenie tesktow na zdania
     */
    private void splitStrings() {

        patternTab = pattern.split("\\.");
        textTab = text.split("\\.");
    }


    /**
     * Porownanie dwoch stringow podanych w parametrach
     *
     * @param _pattern
     * @param _text
     */
    public PlagiarismResult compare(String _pattern, String _text) throws IOException {
        fL = new FileLoader(_pattern, _text);
        fL.loadFiles();
        pattern = fL.getPattern();
        text = fL.getText();
        this.splitStrings();
        int indexStart = 0;
        for (String patternSentence : patternTab) {
            comparisonResult = tP.compareTexts(textTab, patternSentence, indexStart);
            indexStart+=patternSentence.length();
        }

        return comparisonResult;
    }


}
