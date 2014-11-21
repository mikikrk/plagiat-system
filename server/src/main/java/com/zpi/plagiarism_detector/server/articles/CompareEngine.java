package com.zpi.plagiarism_detector.server.articles;

import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;

import java.io.IOException;
import java.util.LinkedList;



public class CompareEngine {
	
	private String pattern = "";
	private String text = "";
	private String[] patternTab;
	private String[] textTab;
	private LinkedList<PlagiarismResult> comparisonResult = new LinkedList<>();
	private FileLoader fL;
	private TextProcessing tP = new TextProcessing();
	
	public CompareEngine(){		
	}
	
	/**
	 *  Dzielenie tesktow na zdania
	 */
	private void splitStrings(){
		
		patternTab = pattern.split("\\.");
		textTab = text.split("\\.");
	}
	
	
	/**
	 * Porownanie dwoch stringow podanych w parametrach
	 * @param _pattern
	 * @param _text
	 */
	public LinkedList<PlagiarismResult> compare(String _pattern,String _text) throws IOException{
		
		fL = new FileLoader(_pattern,_text);
		fL.loadFiles();
		pattern = fL.getPattern();
		text = fL.getText();
		this.splitStrings();
		int i=0;
		for(String patternSentence : patternTab ){
				comparisonResult = tP.compareTexts(text, patternSentence);		
		}		
		
		return comparisonResult;
	}
	
	
}
