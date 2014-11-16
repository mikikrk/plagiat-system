package com.zpi.plagiarism_detector.server.database;

import java.util.Set;
/**
 * Interfejs umozliwiajacy komunikacje z baza danych.
 * 
 */
public interface Dao {

	public Article getArticle(String path);

	public  boolean containsUri(String uri);

	public int addArticle(Article article, Set<String> keywords);

	public  int replaceKeywords(String path, Set<String> keywords);

	public  int addKeywords(String path, Set<String> keywords);

	public  Set<String> getKeywords(String path);

	public  Set<String> findArticesWithAtLeastOne(Set<String> set,
			Type type);

	public  Set<String> findArticesWithAll(Set<String> set, Type type);

	public  int removeAll();

	public  int changePath(String currentPath, String newPath);

	public  int removeArticle(String path);

}