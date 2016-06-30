package fr.treeptik.amazonejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.treeptik.amazonejb.dao.CommandeDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.Commande;

@Stateless
public class CommandeDAOImpl implements CommandeDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	
	@Override
	public Commande save(Commande commande) throws DAOException {
		
		try {			
			if (commande.getId() == null) {
				entityManager.persist(commande);
			} else {
				entityManager.merge(commande);
			}
			return commande;
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CommandeDAO save " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(Commande commande) throws DAOException {
		
		try {			
			entityManager.remove(commande);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CommandeDAO remove " + e.getMessage(), e);
		}
		
	}

	@Override
	public void removeSoft(Commande entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Commande findById(Long id) throws DAOException {
		
		try {			
			return entityManager.find(Commande.class, id);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CommandeDAO findById " + e.getMessage(), e);
		}
	}

	@Override
	public List<Commande> find(Commande commande) throws DAOException {
		
		try {
			TypedQuery<Commande> query = entityManager.createQuery("SELECT c FROM Commande c WHERE c.dateCommande = :dateCommande", Commande.class);
			query.setParameter("nom", commande.getDateCommande());
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CommandeDAO find " + e.getMessage(), e);
		}
	}

	@Override
	public List<Commande> findAll(boolean deleted) throws DAOException {
		
		try {
			TypedQuery<Commande> query = entityManager.createQuery("SELECT c FROM Commande c WHERE c.deleted = :deleted", Commande.class);
			query.setParameter("deleted", deleted);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CommandeDAO findAll " + e.getMessage(), e);
		}
	}


}
