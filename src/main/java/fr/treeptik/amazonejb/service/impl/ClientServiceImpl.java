package fr.treeptik.amazonejb.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import fr.treeptik.amazonejb.dao.ClientDAO;
import fr.treeptik.amazonejb.dao.RoleDAO;
import fr.treeptik.amazonejb.enumeration.RoleUser;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.exception.MethodeException;
import fr.treeptik.amazonejb.exception.ServiceException;
import fr.treeptik.amazonejb.model.Client;
import fr.treeptik.amazonejb.model.Role;
import fr.treeptik.amazonejb.service.ClientService;

@Stateless
public class ClientServiceImpl implements ClientService {

	@EJB
	private ClientDAO clientDAO;
	
	@EJB
	private RoleDAO roleDAO;
	
	@Resource(mappedName = "java:/jms/AmazonQueue")
	private Queue queue;
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);
	
	@Override
	@Transactional
	public Client add(Client client) throws ServiceException {
		
		try {
			if (client.getId() == null) {
				
				Role role = roleDAO.findRoleByEnumRole(RoleUser.ROLE_USER);
				
				Set<Role> roles = new HashSet<>();
				roles.add(role);
				
				client.setRoles(roles);
				
				client = clientDAO.save(client);
				
				sendClient(client);
				
				return client;
				
			} else {				
				throw new MethodeException("Erreur ClientServiceImpl update ===> La personne a un id ===> " + client.getId() + " <=== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl update " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void remove(Client client) throws ServiceException {
		
		try {
			client = clientDAO.findById(client.getId());
			clientDAO.remove(client);
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl remove " + e.getMessage(), e);
		}
		
	}

	@Override
	@Transactional
	public Client update(Client client) throws ServiceException {
			
		try {
			if (client.getId() != null) {
				
				return clientDAO.save(client);
				
			} else {				
				throw new MethodeException("La personne n'a pas d'id ====== Pas normal !!!");
			}
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl update " + e.getMessage(), e);
		}
	}

	@Override
	public Client findById(Long id) throws ServiceException {
		
		try {
			return clientDAO.findById(id);
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl findById " + e.getMessage(), e);
		}
	}

	@Override
	public List<Client> find(Client client) throws ServiceException {
		
		try {
			return clientDAO.find(client);
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl find " + e.getMessage(), e);
		}
	}

	@Override
	public List<Client> findAll() throws ServiceException {
		
		try {
			return clientDAO.findAll(false);
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl findAll " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public Client updateRoleAdminByIdClient(boolean admin, Long id) {
		
		try {
			Client client = clientDAO.findWithAllById(id);
			
			if (client.hasRole(RoleUser.ROLE_ADMIN) && !admin) {
				
				logger.info("======== updateRoleAdminByIdClient ============== On enlève le role amdin du client ===> " + client.getId());
				
				client.removeRole(RoleUser.ROLE_ADMIN);
				clientDAO.mergeAll(client);
			}
			if (!client.hasRole(RoleUser.ROLE_ADMIN) && admin) {
				
				logger.info("========= updateRoleAdminByIdClient ============ On rajoute le role admin au client ===> " + client.getId());
				
				Role role = roleDAO.findRoleByEnumRole(RoleUser.ROLE_ADMIN);
				client.addRole(role);
				clientDAO.mergeAll(client);
			}
			
			return client;
			
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl updateRoleAdminByIdClient " + e.getMessage(), e);
		}
		
	}
	
	public void sendClient(Client client) {
		
		Connection connection = null;
		
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession();
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(client);
			producer.send(objectMessage);
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
