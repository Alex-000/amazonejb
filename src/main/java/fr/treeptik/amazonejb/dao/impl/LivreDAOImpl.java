package fr.treeptik.amazonejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.treeptik.amazonejb.dao.LivreDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.Livre;

@Stateless
public class LivreDAOImpl implements LivreDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	
	@Override
	public Livre save(Livre livre) throws DAOException {
		
		try {			
			if (livre.getId() == null) {
				entityManager.persist(livre);
			} else {
				entityManager.merge(livre);
			}
			return livre;
		} catch (PersistenceException e) {
			throw new DAOException("Erreur LivreDAO save " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(Livre livre) throws DAOException {
		
		try {			
			entityManager.remove(livre);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur LivreDAO remove " + e.getMessage(), e);
		}
		
	}

	@Override
	public void removeSoft(Livre entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Livre findById(Long id) throws DAOException {
		
		try {			
			return entityManager.find(Livre.class, id);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur LivreDAO findById " + e.getMessage(), e);
		}
	}

	@Override
	public List<Livre> find(Livre livre) throws DAOException {
		
		try {
			TypedQuery<Livre> query = entityManager.createQuery("SELECT l FROM Livre l WHERE l.titre = :titre", Livre.class);
			query.setParameter("titre", livre.getTitre());
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur LivreDAO find " + e.getMessage(), e);
		}
	}

	@Override
	public List<Livre> findAll(boolean deleted) throws DAOException {
		
		try {
			TypedQuery<Livre> query = entityManager.createQuery("SELECT l FROM Livre l WHERE l.deleted = :deleted", Livre.class);
			query.setParameter("deleted", deleted);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur LivreDAO findAll " + e.getMessage(), e);
		}
	}


}
