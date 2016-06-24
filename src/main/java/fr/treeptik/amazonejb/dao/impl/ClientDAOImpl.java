package fr.treeptik.amazonejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.treeptik.amazonejb.dao.ClientDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.Client;

@Stateless
public class ClientDAOImpl implements ClientDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	
	@Override
	public Client save(Client client) throws DAOException {
		
		try {			
			if (client.getId() == null) {
				entityManager.persist(client);
			} else {
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
	public void remove(Client client) throws DAOException {
		
		try {			
			entityManager.remove(client);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO remove " + e.getMessage(), e);
		}
		
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
	public List<Client> findAll() throws DAOException {
		
		try {
			return entityManager.createQuery("SELECT c FROM Client c LEFT JOIN FETCH c.roles", Client.class).getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO findAll " + e.getMessage(), e);
		}
	}

}
