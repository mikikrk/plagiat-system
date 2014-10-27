package database;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

class HibernateAccess {

	private static SessionFactory sessionFactory;

	static {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		sessionFactory = cfg.buildSessionFactory();
	}

	int addArticle(String path, Type type, Set<String> keywords,
			ArticleOptionalInfo aoi) {

		int result = 0;

		if (path == null || type == null || keywords == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Article article = getArticle(path, session);
		if (article == null) {

			article = new Article();
			article.setPath(path);
			article.setType(type);
			if (aoi != null) {
				article.setTitle(aoi.getTitle());
				article.setAuthors(aoi.getAuthors());
				article.setSource(aoi.getSource());
				article.setYear(aoi.getYear());
			}

			session.save(article);

			Long articleId = article.getId();
			for (String kw : keywords) {
				addKeyword(kw, articleId, session);
			}
			tx.commit();
			session.flush();
			result = 1;
		}

		return result;
	}

	int setArticleOptionalInfo(String path, ArticleOptionalInfo aoi) {
		int result = 0;

		if (path == null || aoi == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();

		Article article = getArticle(path, session);
		if (article != null) {
			article.setTitle(aoi.getTitle());
			article.setAuthors(aoi.getAuthors());
			article.setSource(aoi.getSource());
			article.setYear(aoi.getYear());
			session.update(article);
			result = 1;
		}
		tx.commit();
		session.flush();

		return result;

	}

	ArticleOptionalInfo getArticleOptionalInfo(String path) {
		ArticleOptionalInfo result = new ArticleOptionalInfo();

		if (path == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();

		Article article = getArticle(path, session);
		if (article != null) {
			result.setTitle(article.getTitle());
			result.setAuthors(article.getAuthors());
			result.setSource(article.getSource());
			result.setYear(article.getYear());

		}
		tx.commit();
		session.flush();

		return result;

	}

	private void addKeyword(String word, Long articleId, Session session) {
		Criterion keywordEq = Restrictions.eq("word", word);
		Criteria crit = session.createCriteria(Keyword.class);
		crit.add(keywordEq);
		Keyword keyword = (Keyword) crit.uniqueResult();
		if (keyword == null) {
			keyword = new Keyword();
			keyword.setWord(word);
			session.save(keyword);

		}
		addAssignment(articleId, keyword.getId(), session);
	}

	private void addAssignment(Long articleId, Long keywordId, Session session) {
		Assignment assignment = new Assignment();
		assignment.setArticleId(articleId);
		assignment.setKeywordId(keywordId);

		session.saveOrUpdate(assignment);
	}

	private void removeKeywords(Long articleId, Session session) {
		String hql = " DELETE FROM " + Assignment.class.getName() + " as asg "
				+ "where asg.articleId=:articleId";

		Query query = session.createQuery(hql);
		query.setLong("articleId", articleId);
		query.executeUpdate();

		hql = " DELETE FROM " + Keyword.class.getName() + " as key "
				+ "where key.id not in " + "(Select keywordId from "
				+ Assignment.class.getName() + ")";

		query = session.createQuery(hql);
		query.executeUpdate();
	}

	int removeArticle(String path) {
		int result = 0;
		if (path == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Criterion pathEq = Restrictions.eq("path", path);
		Criteria crit = session.createCriteria(Article.class);
		crit.add(pathEq);

		Article article = (Article) crit.uniqueResult();

		if (article != null) {
			session.delete(article);
			removeKeywords(article.getId(), session);
			result = 1;
		}
		tx.commit();
		session.flush();

		return result;
	}

	private Article getArticle(String path, Session session) {
		Criterion pathEq = Restrictions.eq("path", path);
		Criteria crit = session.createCriteria(Article.class);
		crit.add(pathEq);

		return (Article) crit.uniqueResult();
	}

	Set<String> getKeywords(String path) {
		Set<String> result = new HashSet<String>();
		if (path == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();

		Article article = getArticle(path, session);

		if (article != null) {

			Long articleId = article.getId();

			String hql = " SELECT ke.word FROM " + Assignment.class.getName()
					+ " as asg, " + Keyword.class.getName() + " as ke "
					+ "where asg.articleId=:articleId AND ke.id=asg.keywordId";

			Query query = session.createQuery(hql);
			query.setLong("articleId", articleId);
			@SuppressWarnings("rawtypes")
			List list = query.list();

			for (Object obj : list) {
				result.add((String) obj);
			}

		}
		tx.commit();
		session.flush();

		return result;

	}

	int setKeywords(String path, Set<String> keywords, boolean append) {
		int result = 0;
		if (path == null || keywords == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();

		Article article = getArticle(path, session);

		if (article != null) {

			Long articleId = article.getId();

			if (!append) {

				removeKeywords(articleId, session);

			}

			for (String kw : keywords) {
				addKeyword(kw, articleId, session);
			}

			result = 1;
		}
		tx.commit();
		session.flush();

		return result;
	}

	Set<String> findArticles(Set<String> kwSet, Type type, boolean all) {
		Set<String> result = new HashSet<String>();
		if (kwSet == null || type == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		boolean firstIt = true;
		for (String kw : kwSet) {
			String sql = " SELECT ar.path FROM  ARTICLE" + " ar"
					+ " JOIN ASSIGNMENT" + " ass ON " + "ar.id=ass.article_Id "
					+ " JOIN " + "KEYWORD" + " ke ON "
					+ " ass.keyword_Id=ke.id WHERE ke.word=:kw "
					+ " AND  ar.type=:typeNr ";

			Query query = session.createSQLQuery(sql);
			query.setString("kw", kw);
			
			query.setInteger("typeNr", type.ordinal());

			@SuppressWarnings("rawtypes")
			List list = query.list();

			if (!all) {
				for (Object obj : list) {

					result.add((String) obj);
				}
			} else {
				Set<String> tempSet = new HashSet<String>();
				for (Object obj : list) {

					tempSet.add((String) obj);

				}

				if (firstIt) {
					result.addAll(tempSet);
					firstIt = false;
				} else {
					result.retainAll(tempSet);
				}
				if (result.size() == 0) {
					break;
				}
			}

		}
		tx.commit();
		session.flush();

		return result;
	}

	int removeAll() {
		int result = 0;
		Session session = null;
		Transaction tx = null;

		session = sessionFactory.openSession();
		tx = session.getTransaction();
		tx.begin();
		String hql = " DELETE FROM " + Assignment.class.getName() + "  ";
		Query query = session.createQuery(hql);
		query.executeUpdate();

		hql = " DELETE FROM " + Keyword.class.getName() + "  ";
		query = session.createQuery(hql);
		query.executeUpdate();

		hql = " DELETE FROM " + Article.class.getName() + "  ";
		query = session.createQuery(hql);
		query.executeUpdate();

		tx.commit();
		session.flush();
		result = 1;

		return result;

	}

	int setType(String path, Type newType) {
		int result = 0;
		if (path == null || newType == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Criterion pathEq = Restrictions.eq("path", path);
		Criteria crit = session.createCriteria(Article.class);
		crit.add(pathEq);

		Article article = (Article) crit.uniqueResult();

		if (article != null) {

			article.setType(newType);
			session.update(article);

			result = 1;
		}

		tx.commit();
		session.flush();

		return result;
	}

	int setPath(String path, String newPath) {
		int result = 0;
		if (path == null || newPath == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Criterion pathEq = Restrictions.eq("path", path);
		Criteria crit = session.createCriteria(Article.class);
		crit.add(pathEq);

		Article article = (Article) crit.uniqueResult();

		if (article != null) {

			article.setPath(newPath);
			session.update(article);

			result = 1;
		}

		tx.commit();
		session.flush();

		return result;
	}

	Type getType(String path) {
		Type result = null;
		if (path == null) {
			return result;
		}

		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Criterion pathEq = Restrictions.eq("path", path);
		Criteria crit = session.createCriteria(Article.class);
		crit.add(pathEq);

		Article article = (Article) crit.uniqueResult();

		if (article != null) {

			result = article.getType();

		}

		tx.commit();
		session.flush();

		return result;

	}

}
