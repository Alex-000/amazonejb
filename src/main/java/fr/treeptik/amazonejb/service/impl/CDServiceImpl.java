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

import fr.treeptik.amazonejb.dao.CDDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.exception.ServiceException;
import fr.treeptik.amazonejb.model.CD;
import fr.treeptik.amazonejb.service.CDService;

@Stateless
public class CDServiceImpl implements CDService {

	@EJB
	private CDDAO cdDAO;
	
	@Resource(mappedName = "java:/jms/AmazonArticleQueue")
	private Queue queue;
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	private static final Logger logger = Logger.getLogger(CDServiceImpl.class);
	
	@Override
	public List<CD> findAll() throws ServiceException {
		
		try {
			return cdDAO.findAll(false);
		} catch (DAOException e) {
			throw new ServiceException("Erreur CDServiceImpl findAll " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public CD add(CD cd) throws ServiceException {
		
		try {
			if (cd.getId() == null) {
				
				logger.debug("\n============================ Dans add ===> envoi du cd dans la queue\n");
				
				sendCD(cd);
				
				return cd;
				
				// return cdDAO.save(cd);
				
			} else {				
				throw new ServiceException("Erreur CDServiceImpl add ===> le cd a un id ===> " + cd.getId() + " <=== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur CDServiceImpl add " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(CD cd) throws ServiceException {
		
		try {
			cd = cdDAO.findById(cd.getId());
			cdDAO.remove(cd);
		} catch (DAOException e) {
			throw new ServiceException("Erreur CDServiceImpl remove " + e.getMessage(), e);
		}
		
	}

	@Override
	public CD update(CD cd) throws ServiceException {
		
		try {
			if (cd.getId() != null) {
				
				return cdDAO.save(cd);
				
			} else {				
				throw new ServiceException("Le cd n'a pas d'id ====== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur CDServiceImpl update " + e.getMessage(), e);
		}
	}

	@Override
	public CD findById(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CD> find(CD entite) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void sendCD(CD cd) {
		
		Connection connection = null;
		
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession();
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(cd);
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
