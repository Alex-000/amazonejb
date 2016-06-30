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

import fr.treeptik.amazonejb.dao.DVDDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.exception.ServiceException;
import fr.treeptik.amazonejb.model.DVD;
import fr.treeptik.amazonejb.service.DVDService;

@Stateless
public class DVDServiceImpl implements DVDService {

	@EJB
	private DVDDAO dvdDAO;
	
	@Resource(mappedName = "java:/jms/AmazonArticleQueue")
	private Queue queue;
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	private static final Logger logger = Logger.getLogger(DVDServiceImpl.class);
	
	@Override
	public List<DVD> findAll() throws ServiceException {
		
		try {
			return dvdDAO.findAll(false);
		} catch (DAOException e) {
			throw new ServiceException("Erreur DVDServiceImpl findAll " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public DVD add(DVD dvd) throws ServiceException {
		
		try {
			if (dvd.getId() == null) {
				
				logger.debug("\n============================ Dans add ===> envoi du dvd dans la queue\n");
				
				sendDVD(dvd);
				
				return dvd;
				
				// return dvdDAO.save(dvd);
				
			} else {				
				throw new ServiceException("Erreur DVDServiceImpl add ===> le dvd a un id ===> " + dvd.getId() + " <=== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur DVDServiceImpl add " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(DVD dvd) throws ServiceException {
		
		try {
			dvd = dvdDAO.findById(dvd.getId());
			dvdDAO.remove(dvd);
		} catch (DAOException e) {
			throw new ServiceException("Erreur DVDServiceImpl remove " + e.getMessage(), e);
		}
		
	}

	@Override
	public DVD update(DVD dvd) throws ServiceException {
		
		try {
			if (dvd.getId() != null) {
				
				return dvdDAO.save(dvd);
				
			} else {				
				throw new ServiceException("Le dvd n'a pas d'id ====== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur DVDServiceImpl update " + e.getMessage(), e);
		}
	}

	@Override
	public DVD findById(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DVD> find(DVD entite) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void sendDVD(DVD dvd) {
		
		Connection connection = null;
		
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession();
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(dvd);
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
