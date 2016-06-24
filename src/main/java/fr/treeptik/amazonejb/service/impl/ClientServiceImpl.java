package fr.treeptik.amazonejb.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import fr.treeptik.amazonejb.dao.ClientDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.exception.ServiceException;
import fr.treeptik.amazonejb.model.Client;
import fr.treeptik.amazonejb.service.ClientService;

@Stateless
public class ClientServiceImpl implements ClientService {

	@EJB
	private ClientDAO clientDAO;
	
	@Override
	@Transactional
	public Client add(Client client) throws ServiceException {
		
		try {
			return clientDAO.save(client);
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl add " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(Client client) throws ServiceException {
		
		try {
			client = clientDAO.findById(client.getId());
			clientDAO.remove(client);
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl remove " + e.getMessage(), e);
		}
		
	}

	@Override
	public Client update(Client client) throws ServiceException {
			
		try {
			return clientDAO.save(client);
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
			return clientDAO.findAll();
		} catch (DAOException e) {
			throw new ServiceException("Erreur ClientServiceImpl findAll " + e.getMessage(), e);
		}
	}

}
