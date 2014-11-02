package com.zpi.plagiarism_detector.server.database;

import java.util.Set;

/**
 * Interfejs umo�liwiaj�cy komunikacj� z baz� danych.
 * 
 */
public interface Dao {

	/**
	 * dodanie artyku�u do bazy danych
	 * 
	 * @param path
	 *            scie�ka do pliku
	 * @param type
	 *            artyku�u (EN angielski, PL polski, CO kod)
	 * @param keywords
	 *            zbi�r s��w kluczowych
	 * 
	 * */
	public int addArticle(String path, Type type, Set<String> keywords);

	/** j.w., umozliwia podanie opcjonalnychh informacji */
	public int addArticle(String path, Type type, Set<String> keywords,
			ArticleOptionalInfo aoi);

	/** ustawienie opcjonalnych informacji, dla istniej�cego w bazie artyku�u */
	public int setArticleOptionalInfo(String path, ArticleOptionalInfo aoi);

	/** zwraca opcjonalne informacje dla artyku�u o podanej sciezce */
	public ArticleOptionalInfo getArticleOptionalInfo(String path);

	/**
	 * ustawia s�owa kluczowe dla artyku�u o podanej �cie�ce (poprzednie zostaj�
	 * usuniete)
	 */
	public int setKeywords(String path, Set<String> keywords);

	/**
	 * dodaje s�owa kluczowe do artyku�u o podanej �cie�ce (poprzednie zostaj�
	 * zachowane)
	 * 
	 * @return 1 -udane, 0-nieudane
	 */
	public int addKeywords(String path, Set<String> keywords);

	/** zwraca s�owa kluczowe przypisane do artyku�u o podanej scie�ce */
	public Set<String> getKeywords(String path);

	/**
	 * zwraca �cie�ki do artyku��w o podanym typie i z przynajmniej jednym ze
	 * s��w
	 */
	public Set<String> findArticesWithAtLeastOne(Set<String> set, Type type);

	/**
	 * zwraca �cie�ki do artyku��w o podanym typie i ze wszystkimi s�owami
	 * kluczowymi
	 */
	public Set<String> findArticesWithAll(Set<String> set, Type type);

	/**
	 * usuwa wszystkie rekordy z bazy
	 * 
	 * @return 1 -udane, 0-nieudane
	 * 
	 * */
	public abstract int removeAll();

	/**
	 * ustawia typ artyku�u o podanej scie�ce
	 * 
	 * @return 1 udane, 0 nieudane
	 * */
	public abstract int setType(String path, Type newType);

	/**
	 * zmienia �cie�ke artyku�u
	 * 
	 * @return 1 udane, 0 nieudane
	 * */
	public abstract int setPath(String currentPath, String newPath);

	/**
	 * usuwa z bazy artyku� o podanej scie�ce
	 * 
	 * @return 1 udane, 0 nieudane
	 * */
	public abstract int removeArticle(String path);

	/**
	 * zwraca typ artyku�u
	 * */
	public abstract Type getType(String path);

}