package fr.treeptik.amazonejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import fr.treeptik.amazonejb.dao.ArticleDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.Article;

@Stateless
public class ArticleDAOImpl implements ArticleDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	private static final Logger logger = Logger.getLogger(ArticleDAOImpl.class);
	
	@Override
	public List<Article> findAll(boolean deleted) throws DAOException {
		
		try {
			TypedQuery<Article> query = entityManager.createQuery("SELECT a FROM Article a WHERE a.deleted = :deleted", Article.class);
			query.setParameter("deleted", deleted);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ArticleDAOImpl findAll " + e.getMessage(), e);
		}

	}

	@Override
	public Article save(Article article) throws DAOException {
		
		try {			
			if (article.getId() == null) {
				logger.info("================ Dans save de ArticleDAOImpl avec un id null");
				entityManager.persist(article);
				logger.info("============================" + article);
			} else {
				logger.info("================ Dans save de ArticleDAOImpl avec un id ===> " + article.getId());
				Query query = entityManager.createQuery("UPDATE Article a SET a.genre = :genre, a.titre = :titre, a.stock = :stock, a.prix = :prix WHERE a.id = :id");
				query.setParameter("genre", article.getGenre());
				query.setParameter("titre", article.getTitre());
				query.setParameter("stock", article.getStock());
				query.setParameter("prix", article.getPrix());
				query.setParameter("id", article.getId());
				query.executeUpdate();
			}
			return article;
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ArticleDAOImpl save " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(Article entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void removeSoft(Article entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Article findById(Long id) throws DAOException {
		
		try {			
			return entityManager.find(Article.class, id);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ArticleDAO findById " + e.getMessage(), e);
		}
	}

	@Override
	public List<Article> find(Article entite) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}


}
