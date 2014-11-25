package com.zpi.plagiarism_detector.server.articles;

import java.util.LinkedHashMap;
import java.util.Map;

import com.zpi.plagiarism_detector.commons.database.DocumentType;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismFragment;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;


public class TextProcessing {

    private String patternPath;
    private String textPath;
    private double sentenceWordsCount;
    private double repeatedWords;
    private PlagiarismResult plagiarismResult = new PlagiarismResult();
    private Map<PlagiarismFragment, PlagiarismFragment> map = new LinkedHashMap<PlagiarismFragment, PlagiarismFragment>();

    public TextProcessing() {
    	plagiarismResult.setType(DocumentType.TEXT);
    }

    /**
     * W�asciwe por�wnanie
     *
     * @param str
     * @param p
     * @param patternStart
     */
    public PlagiarismResult compareTexts(String[] str, String p,int patternStart) {
        int textStart = 0;
        String tmpPattern = p;
        for(String textSentence : str){
    		repeatedWords=0;
    		String tmpText = textSentence;
        	String result = textSentence.replaceAll("[()-+.^:,]","");
        	result=result.replaceAll("\\b[\\w']{1,2}\\b", "");
        	result = result.replaceAll("\\s{2,}", " ");
        	String[] splited = result.split("\\s+");
        	sentenceWordsCount=splited.length-1;
        	p=p.replaceAll("[()-+.^:,]","");
        	p = p.replaceAll("\\b[\\w']{1,2}\\b", "");
            p = p.replaceAll("\\s{2,}", " ");
        	String[] splitedPattern = p.split("\\s+");
        	for(String test: splitedPattern) {
        		if(textSentence.toLowerCase().contains(test.toLowerCase())){
        			repeatedWords++;
        		}   		
        	}
        	
    		repeatedWords-=1;  

    		if(repeatedWords/sentenceWordsCount>ServerProperties.MIN_WORD_SIMILARITY_PERCENTAGE/(double)100){
    			 map.put(new PlagiarismFragment(tmpPattern, patternStart, patternStart+tmpPattern.length(), tmpPattern.length()),
    		             new PlagiarismFragment(tmpText, textStart, textStart+tmpText.length(), tmpText.length()));
    		}
    		textStart+=tmpText.length();
        }
        plagiarismResult.setPlagiarisedFragments(map);
        return plagiarismResult;
    }

}


