package com.zpi.plagiarism_detector.commons.protocol;

import java.io.Serializable;
import java.util.List;

public class DocumentData implements Serializable {
    private String title;
    private List<String> keywords;
    private String article;
    private List<String> codes;
	public DocumentData(String title, List<String> keywords, String article,
			List<String> codes) {
		super();
		this.title = title;
		this.keywords = keywords;
		this.article = article;
		this.codes = codes;
	}
	public DocumentData(){
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public List<String> getCodes() {
		return codes;
	}
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
}
