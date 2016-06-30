package fr.treeptik.amazonejb.utils;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import fr.treeptik.amazonejb.model.Article;

@Stateful
public class ArticleQueueUtil {
	
	private static final Logger logger = Logger.getLogger(ArticleQueueUtil.class);

	@Resource(mappedName = "java:/jms/AmazonArticleQueue")
	private static Queue queue;
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private static ConnectionFactory connectionFactory;
	
	private static Connection connection;
	private static Session session;
	private static MessageProducer messageProducer;
	private static ObjectMessage objectMessage;

	
	public static void sendArticle(Article article) {
		
		try {
			connection = getConnection();
			session = getSession();
			messageProducer = getMessageProducer();
			objectMessage = getObjectMessage();
			
			objectMessage.setObject(article);
			messageProducer.send(objectMessage);
			
		} catch (JMSException e) {
			logger.log(Level.ERROR, e);
		}
	}
	
	
	private static Connection getConnection() {
		
		if (connection == null) {
			try {
				connection = connectionFactory.createConnection();
			} catch (JMSException e) {
				logger.log(Level.ERROR, e);
			}
		}
		return connection;
	}
	
	private static Session getSession() {
		
		if (session == null) {
			try {
				session = connection.createSession();
			} catch (JMSException e) {
				logger.log(Level.ERROR, e);
			}
		}
		return session;
	}
	
	private static MessageProducer getMessageProducer() {
		
		if (messageProducer == null) {
			try {
				messageProducer = session.createProducer(queue);
			} catch (JMSException e) {
				logger.log(Level.ERROR, e);
			}
		}
		return messageProducer;
	}
	
	private static ObjectMessage getObjectMessage() {
		
		if (objectMessage == null) {
			try {
				objectMessage = session.createObjectMessage();
			} catch (JMSException e) {
				logger.log(Level.ERROR, e);
			}
		}
		return objectMessage;
	}
	
}
