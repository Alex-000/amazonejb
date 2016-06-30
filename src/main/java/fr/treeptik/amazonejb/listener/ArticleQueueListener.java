package fr.treeptik.amazonejb.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import fr.treeptik.amazonejb.dao.ArticleDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.Article;

@MessageDriven(activationConfig = {
		
		@ActivationConfigProperty(propertyName =  "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName =  "destination", propertyValue = "java:/jms/AmazonArticleQueue")
})
public class ArticleQueueListener implements MessageListener {

	@EJB
	private ArticleDAO articleDAO;
	
	private static final Logger logger = Logger.getLogger(ArticleQueueListener.class);
	
	@Override
	public void onMessage(Message message) {
		
		ObjectMessage msg = (ObjectMessage) message;
		
		try {
			Object object = msg.getObject();
			
			if (object instanceof Article) {
				
				articleDAO.save((Article) object);
				
			}
			
		} catch (JMSException | DAOException e) {
			logger.log(Level.ERROR, e);
		}
	}

}
