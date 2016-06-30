package fr.treeptik.amazonejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.treeptik.amazonejb.dao.CDDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.CD;

@Stateless
public class CDDAOImpl implements CDDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	
	@Override
	public CD save(CD cd) throws DAOException {
		
		try {			
			if (cd.getId() == null) {
				entityManager.persist(cd);
			} else {
				entityManager.merge(cd);
			}
			return cd;
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CDDAO save " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(CD cd) throws DAOException {
		
		try {			
			entityManager.remove(cd);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CDDAO remove " + e.getMessage(), e);
		}
		
	}

	@Override
	public void removeSoft(CD entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CD findById(Long id) throws DAOException {
		
		try {			
			return entityManager.find(CD.class, id);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CDDAO findById " + e.getMessage(), e);
		}
	}

	@Override
	public List<CD> find(CD cd) throws DAOException {
		
		try {
			TypedQuery<CD> query = entityManager.createQuery("SELECT c FROM CD c WHERE c.titre = :titre", CD.class);
			query.setParameter("titre", cd.getTitre());
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CDDAO find " + e.getMessage(), e);
		}
	}

	@Override
	public List<CD> findAll(boolean deleted) throws DAOException {
		
		try {
			TypedQuery<CD> query = entityManager.createQuery("SELECT c FROM CD c WHERE c.deleted = :deleted", CD.class);
			query.setParameter("deleted", deleted);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur CDDAO findAll " + e.getMessage(), e);
		}
	}


}
