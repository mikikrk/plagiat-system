package com.zpi.plagiarism_detector.server.database;

import java.util.Set;

public class DaoImp implements Dao {
	private HibernateAccess hibernateAccess;

	public DaoImp() {
		hibernateAccess = new HibernateAccess();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#addArticle(java.lang.String, database.Type,
	 * java.util.Set)
	 */
	@Override
	public int addArticle(String path, Type type, Set<String> keywords) {
		return hibernateAccess.addArticle(path, type, keywords, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#addArticle(java.lang.String, database.Type,
	 * java.util.Set, database.ArticleOptionalInfo)
	 */
	@Override
	public int addArticle(String path, Type type, Set<String> keywords,
			ArticleOptionalInfo aoi) {
		return hibernateAccess.addArticle(path, type, keywords, aoi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#setArticleOptionalInfo(java.lang.String,
	 * database.ArticleOptionalInfo)
	 */
	@Override
	public int setArticleOptionalInfo(String path, ArticleOptionalInfo aoi) {
		return hibernateAccess.setArticleOptionalInfo(path, aoi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#getArticleOptionalInfo(java.lang.String)
	 */
	@Override
	public ArticleOptionalInfo getArticleOptionalInfo(String path) {
		return hibernateAccess.getArticleOptionalInfo(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#setKeywords(java.lang.String, java.util.Set)
	 */
	@Override
	public int setKeywords(String path, Set<String> keywords) {
		return hibernateAccess.setKeywords(path, keywords, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#addKeywords(java.lang.String, java.util.Set)
	 */
	@Override
	public int addKeywords(String path, Set<String> keywords) {
		return hibernateAccess.setKeywords(path, keywords, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#getKeywords(java.lang.String)
	 */
	@Override
	public Set<String> getKeywords(String path) {
		return hibernateAccess.getKeywords(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#findArticesWithAtLeastOne(java.util.Set, database.Type)
	 */
	@Override
	public Set<String> findArticesWithAtLeastOne(Set<String> set, Type type) {
		return hibernateAccess.findArticles(set, type, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#findArticesWithAll(java.util.Set, database.Type)
	 */
	@Override
	public Set<String> findArticesWithAll(Set<String> set, Type type) {
		return hibernateAccess.findArticles(set, type, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#removeAll()
	 */
	@Override
	public int removeAll() {
		return hibernateAccess.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#setType(java.lang.String, database.Type)
	 */
	@Override
	public int setType(String path, Type newType) {
		return hibernateAccess.setType(path, newType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#setPath(java.lang.String, java.lang.String)
	 */
	@Override
	public int setPath(String currentPath, String newPath) {
		return hibernateAccess.setPath(currentPath, newPath);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#removeArticle(java.lang.String)
	 */
	@Override
	public int removeArticle(String path) {
		return hibernateAccess.removeArticle(path);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.Dao#removeArticle(java.lang.String)
	 */
	@Override
	public Type getType(String path) {
		return hibernateAccess.getType(path);
	}

}
