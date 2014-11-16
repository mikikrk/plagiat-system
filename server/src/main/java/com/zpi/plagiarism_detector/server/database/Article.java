package com.zpi.plagiarism_detector.server.database;

/**
 * Klasa zawierajaca info o artykule zapisywane w bazie
 * 
 */

public class Article {
	private Long id;
	private String path;
	private Type type;
	private String title;
	private String uri;

	public String getUri() {
		return uri;
	}

	Article() {

	}

	public Article(String path, Type type) {
		this.path = path;
		this.type = type;
	}

	public void setUri(String source) {
		this.uri = source;
	}

	Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
