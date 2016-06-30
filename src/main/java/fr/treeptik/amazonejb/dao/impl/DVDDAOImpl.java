package fr.treeptik.amazonejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import fr.treeptik.amazonejb.dao.DVDDAO;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.DVD;

@Stateless
public class DVDDAOImpl implements DVDDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	
	@Override
	public DVD save(DVD dvd) throws DAOException {
		
		try {			
			if (dvd.getId() == null) {
				entityManager.persist(dvd);
			} else {
				entityManager.merge(dvd);
			}
			return dvd;
		} catch (PersistenceException e) {
			throw new DAOException("Erreur DVDDAO save " + e.getMessage(), e);
		}
	}

	@Override
	public void remove(DVD dvd) throws DAOException {
		
		try {			
			entityManager.remove(dvd);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur DVDDAO remove " + e.getMessage(), e);
		}
		
	}
	
	@Override
	public void removeSoft(DVD entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DVD findById(Long id) throws DAOException {
		
		try {			
			return entityManager.find(DVD.class, id);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur DVDDAO findById " + e.getMessage(), e);
		}
	}

	@Override
	public List<DVD> find(DVD dvd) throws DAOException {
		
		try {
			TypedQuery<DVD> query = entityManager.createQuery("SELECT d FROM DVD d WHERE d.titre = :titre", DVD.class);
			query.setParameter("titre", dvd.getTitre());
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur DVDDAO find " + e.getMessage(), e);
		}
	}

	@Override
	public List<DVD> findAll(boolean deleted) throws DAOException {
		
		try {
			TypedQuery<DVD> query = entityManager.createQuery("SELECT d FROM DVD d WHERE d.deleted = :deleted", DVD.class);
			query.setParameter("deleted", deleted);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur DVDDAO findAll " + e.getMessage(), e);
		}
	}



}
