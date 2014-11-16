package com.zpi.plagiarism_detector.commons.protocol;

import java.io.Serializable;
import java.util.Map;

public class PlagiarismDetectionResult implements Serializable {
	public enum Type {CODE, TEXT};
    
    private String newDocument;        //to co użytkownik przesłał
    private String existingDocument;   //to do czego jest porównywany dokument użytkownika
    //<newDocument, existingDocument>
    private Map<PlagiarismFragment, PlagiarismFragment> plagiarisedFragments;
    private Type type;
   
    public PlagiarismDetectionResult(String newDocument,
			String existingDocument,
			Map<PlagiarismFragment, PlagiarismFragment> plagiarisedFragments,
			Type type) {
		super();
		this.newDocument = newDocument;
		this.existingDocument = existingDocument;
		this.plagiarisedFragments = plagiarisedFragments;
		this.type = type;
	}
    public PlagiarismDetectionResult(){
    	super();
    }
    
	public class PlagiarismFragment implements Serializable{
            private String fragment;
            private int begin;      //miejsce chara rozpoczynajacego dany fragment
            private int end;
            private int size;
			public PlagiarismFragment(String fragment, int begin, int end,
					int size) {
				super();
				this.fragment = fragment;
				this.begin = begin;
				this.end = end;
				this.size = size;
			}
			public PlagiarismFragment(){
				super();
			}
			public String getFragment() {
				return fragment;
			}
			public void setFragment(String fragment) {
				this.fragment = fragment;
			}
			public int getBegin() {
				return begin;
			}
			public void setBegin(int begin) {
				this.begin = begin;
			}
			public int getEnd() {
				return end;
			}
			public void setEnd(int end) {
				this.end = end;
			}
			public int getSize() {
				return size;
			}
			public void setSize(int size) {
				this.size = size;
			}
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
