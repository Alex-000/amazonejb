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

import fr.treeptik.amazonejb.dao.LivreDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.exception.ServiceException;
import fr.treeptik.amazonejb.model.Livre;
import fr.treeptik.amazonejb.service.LivreService;

@Stateless
public class LivreServiceImpl implements LivreService {

	@EJB
	private LivreDAO livreDAO;
	
	@Resource(mappedName = "java:/jms/AmazonArticleQueue")
	private Queue queue;
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	private static final Logger logger = Logger.getLogger(LivreServiceImpl.class);
	
	@Override
	public List<Livre> findAll() throws ServiceException {
		
		try {
			return livreDAO.findAll(false);
		} catch (DAOException e) {
			throw new ServiceException("Erreur LivreServiceImpl findAll " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public Livre add(Livre livre) throws ServiceException {
		
		try {
			if (livre.getId() == null) {
				
				logger.debug("\n============================ Dans add ===> envoi du livre dans la queue\n");
				
				sendLivre(livre);
				
				return livre;
				
				// return livreDAO.save(livre);
				
			} else {				
				throw new ServiceException("Erreur LivreServiceImpl add ===> le livre a un id ===> " + livre.getId() + " <=== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur LivreServiceImpl add " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(Livre livre) throws ServiceException {
		
		try {
			livre = livreDAO.findById(livre.getId());
			livreDAO.remove(livre);
		} catch (DAOException e) {
			throw new ServiceException("Erreur LivreServiceImpl remove " + e.getMessage(), e);
		}
		
	}

	@Override
	public Livre update(Livre livre) throws ServiceException {
		
		try {
			if (livre.getId() != null) {
				
				return livreDAO.save(livre);
				
			} else {				
				throw new ServiceException("Le livre n'a pas d'id ====== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur LivreServiceImpl update " + e.getMessage(), e);
		}
	}

	@Override
	public Livre findById(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Livre> find(Livre entite) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void sendLivre(Livre livre) {
		
		Connection connection = null;
		
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession();
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(livre);
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
