package database;

import java.util.Set;

/**
 * Interfejs umo¿liwiaj¹cy komunikacjê z baz¹ danych.
 * 
 */
public interface Dao {

	/**
	 * dodanie artyku³u do bazy danych
	 * 
	 * @param path
	 *            scie¿ka do pliku
	 * @param type
	 *            artyku³u (EN angielski, PL polski, CO kod)
	 * @param keywords
	 *            zbiór s³ów kluczowych
	 * 
	 * */
	public int addArticle(String path, Type type, Set<String> keywords);

	/** j.w., umozliwia podanie opcjonalnychh informacji */
	public int addArticle(String path, Type type, Set<String> keywords,
			ArticleOptionalInfo aoi);

	/** ustawienie opcjonalnych informacji, dla istniej¹cego w bazie artyku³u */
	public int setArticleOptionalInfo(String path, ArticleOptionalInfo aoi);

	/** zwraca opcjonalne informacje dla artyku³u o podanej sciezce */
	public ArticleOptionalInfo getArticleOptionalInfo(String path);

	/**
	 * ustawia s³owa kluczowe dla artyku³u o podanej œcie¿ce (poprzednie zostaj¹
	 * usuniete)
	 */
	public int setKeywords(String path, Set<String> keywords);

	/**
	 * dodaje s³owa kluczowe do artyku³u o podanej œcie¿ce (poprzednie zostaj¹
	 * zachowane)
	 * 
	 * @return 1 -udane, 0-nieudane
	 */
	public int addKeywords(String path, Set<String> keywords);

	/** zwraca s³owa kluczowe przypisane do artyku³u o podanej scie¿ce */
	public Set<String> getKeywords(String path);

	/**
	 * zwraca œcie¿ki do artyku³ów o podanym typie i z przynajmniej jednym ze
	 * s³ów
	 */
	public Set<String> findArticesWithAtLeastOne(Set<String> set, Type type);

	/**
	 * zwraca œcie¿ki do artyku³ów o podanym typie i ze wszystkimi s³owami
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
	 * ustawia typ artyku³u o podanej scie¿ce
	 * 
	 * @return 1 udane, 0 nieudane
	 * */
	public abstract int setType(String path, Type newType);

	/**
	 * zmienia œcie¿ke artyku³u
	 * 
	 * @return 1 udane, 0 nieudane
	 * */
	public abstract int setPath(String currentPath, String newPath);

	/**
	 * usuwa z bazy artyku³ o podanej scie¿ce
	 * 
	 * @return 1 udane, 0 nieudane
	 * */
	public abstract int removeArticle(String path);

	/**
	 * zwraca typ artyku³u
	 * */
	public abstract Type getType(String path);

}