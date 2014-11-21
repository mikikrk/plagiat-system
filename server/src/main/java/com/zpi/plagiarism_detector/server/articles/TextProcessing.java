package com.zpi.plagiarism_detector.server.articles;

import com.zpi.plagiarism_detector.commons.database.DocumentType;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismFragment;
import com.zpi.plagiarism_detector.commons.protocol.plagiarism.PlagiarismResult;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TextProcessing {
		
	private String patternPath;
	private String textPath;
	private LinkedList<PlagiarismResult> comparisonResults = new LinkedList<>();
	private Map<PlagiarismFragment, PlagiarismFragment> map = new HashMap<PlagiarismFragment,PlagiarismFragment>();
	
	public TextProcessing(){
	
	}
	
	/**
	 * W�asciwe por�wnanie 
	 * @param str 
	 * @param p
	 */
	public LinkedList<PlagiarismResult> compareTexts(String str, String p){
		int m = p.length();
		int[] next = new int[m];
		next = getNext(p);
		LinkedList<PlagiarismResult> results = findKMPSub(str,p,next);
		System.out.println("HEJ      "+results.size());
		if(results.size()!=0)
			return results;
		else
			return null;
	}
	
	
	public int[] getNext(String p){
		int i=1,j=0;
		int[] next = new int[p.length()+2];
		char[] pattern = p.toCharArray();
		
		next[0] = -1;
		next[1] = 0;
		while(i<p.length()-1){
			if(pattern[i] == pattern[j]){
				i++;
				j++;
				next[i] = next[j];
			}
			else if(j == 0){
				next[i+1] = 0;
				i++;
			}
			else{
				j = next[j];
			}
		}
		return next;
	}
		
	/**
	 * Znajduje podstring p w przeszukiwanym tekscie str
	 */
	public LinkedList<PlagiarismResult> findKMPSub(String str, String p, int next[]){
		char[] string = str.toCharArray();
		char[] pattern = p.toCharArray();
		int i = 0;
		int j=0;
		while(i<str.length()){
			if(j == -1 || string[i] == pattern[j]){
				i++;
				j++;
			}
			else{
				j = next[j];
			}
			if ( j == p.length()){
				j=0;
				map.put(new PlagiarismFragment(p,0, 0,p.length()),
						new PlagiarismFragment(str, i-p.length(), i, p.length()));
					System.out.println(i);		
				comparisonResults.add(new PlagiarismResult("", "",map, DocumentType.TEXT));

			}else{
				int v = -1;
			}
		}
	if(comparisonResults.size()!=0)
		return comparisonResults;
	else 
		return null;
	}	
}


