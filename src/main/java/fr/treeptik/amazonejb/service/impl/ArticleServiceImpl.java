package fr.treeptik.amazonejb.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import fr.treeptik.amazonejb.dao.ArticleDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.exception.ServiceException;
import fr.treeptik.amazonejb.model.Article;
import fr.treeptik.amazonejb.service.ArticleService;
import fr.treeptik.amazonejb.utils.ArticleQueueUtil;

@Stateless
public class ArticleServiceImpl implements ArticleService {

	@EJB
	private ArticleDAO articleDAO;
	
	@Resource(mappedName = "java:/jms/AmazonArticleQueue")
	private Queue queue;
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	private static final Logger logger = Logger.getLogger(ArticleServiceImpl.class);
	
	@Override
	public List<Article> findAll() throws ServiceException {
		
		try {
			return articleDAO.findAll(false);
		} catch (DAOException e) {
			throw new ServiceException("Erreur ArticleServiceImpl findAll " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public Article add(Article article) throws ServiceException {
		
		try {
			if (article.getId() == null) {
				
				logger.debug("\n============================ Dans add ===> envoi de l'article dans la queue\n");
				
				//ArticleQueueUtil.sendArticle(article);
				sendArticle(article);
				
				return article;
				
				// return articleDAO.save(article);
				
			} else {				
				throw new ServiceException("Erreur ArticleServiceImpl add ===> l'article a un id ===> " + article.getId() + " <=== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur ArticleServiceImpl add " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(Article article) throws ServiceException {
		
		try {
			article = articleDAO.findById(article.getId());
			articleDAO.remove(article);
		} catch (DAOException e) {
			throw new ServiceException("Erreur ArticleServiceImpl remove " + e.getMessage(), e);
		}
		
	}

	@Override
	public Article update(Article article) throws ServiceException {
		
		try {
			if (article.getId() != null) {
				
				return articleDAO.save(article);
				
			} else {				
				throw new ServiceException("L'article n'a pas d'id ====== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur ArticleServiceImpl update " + e.getMessage(), e);
		}
	}

	@Override
	public Article findById(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> find(Article entite) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void sendArticle(Article article) {
		
		Connection connection = null;
		
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession();
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(article);
			producer.send(objectMessage);
			
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
