package fr.treeptik.amazonejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import fr.treeptik.amazonejb.dao.ClientDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.Client;

@Stateless
public class ClientDAOImpl implements ClientDAO {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = Logger.getLogger(ClientDAOImpl.class);
	
	@Override
	public Client save(Client client) throws DAOException {
		
		try {			
			if (client.getId() == null) {
				logger.info("================ Dans save de clientDAOImpl avec un id null");
				entityManager.persist(client);
			} else {
				logger.info("================ Dans save de clientDAOImpl avec un id ===> " + client.getId());
				Query query = entityManager.createQuery("UPDATE Client c SET c.login = :login, c.password = :password, c.nom = :nom, c.prenom = :prenom, c.dateNaissance = :dateNaissance WHERE c.id = :id");
				query.setParameter("login", client.getLogin());
				query.setParameter("password", client.getPassword());
				query.setParameter("nom", client.getNom());
				query.setParameter("prenom", client.getPrenom());
				query.setParameter("dateNaissance", client.getDateNaissance());
				query.setParameter("id", client.getId());
				query.executeUpdate();
				//entityManager.merge(client);
			}
			return client;
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO save " + e.getMessage(), e);
		}
	}
	

	@Override
	public Client mergeAll(Client client) throws DAOException {
		
		try {			
			if (client.getId() == null) {
				logger.info("================ Dans mergeAll de clientDAOImpl avec un id null");
				entityManager.persist(client);
			} else {
				logger.info("================ Dans mergeAll de clientDAOImpl avec un id ===> " + client.getId());

				entityManager.merge(client);
			}
			return client;
			
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO save " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(Client client) throws DAOException {
		
		try {			
			entityManager.remove(client);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO remove " + e.getMessage(), e);
		}
		
	}
	

	@Override
	public void removeSoft(Client entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Client findById(Long id) throws DAOException {
		
		try {			
			return entityManager.find(Client.class, id);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO findById " + e.getMessage(), e);
		}
	}

	@Override
	public List<Client> find(Client client) throws DAOException {
		
		try {
			TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c WHERE c.nom = :nom AND c.prenom = :prenom", Client.class);
			query.setParameter("nom", client.getNom());
			query.setParameter("prenom", client.getPrenom());
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO find " + e.getMessage(), e);
		}
	}

	@Override
	public List<Client> findAll(boolean deleted) throws DAOException {
		
		logger.debug("================ Dans findAll de clientDAOImpl");
		
		try {
			TypedQuery<Client> query = entityManager.createQuery("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.roles WHERE c.deleted = :deleted", Client.class);
			query.setParameter("deleted", deleted);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO findAll " + e.getMessage(), e);
		}
	}

	@Override
	public Client findWithAllById(Long id) throws DAOException {
		
		logger.debug("================ Dans findWithRolesById de clientDAOImpl");
		
		try {
			TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c LEFT JOIN FETCH c.roles LEFT JOIN FETCH c.commandes WHERE c.id = :id", Client.class);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO findWithRolesById " + e.getMessage(), e);
		}
	}




}
