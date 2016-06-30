package fr.treeptik.amazonejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import fr.treeptik.amazonejb.dao.RoleDAO;
import fr.treeptik.amazonejb.enumeration.RoleUser;
import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.Role;

@Stateless
public class RoleDAOImpl implements RoleDAO {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = Logger.getLogger(RoleDAOImpl.class);
	
	@Override
	public Role save(Role role) throws DAOException {
		
		try {			
			entityManager.persist(role);
		} catch (PersistenceException e) {
			throw new DAOException("Erreur RoleDAO save " + e.getMessage(), e);
		}
		
		return role;
	}

	@Override
	public void remove(Role entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void removeSoft(Role entite) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Role findById(Long id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> find(Role entite) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> findAll(boolean deleted) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findRoleByEnumRole(RoleUser role) {
		
		logger.debug("================ Dans findRoleByEnumRole de RoleDAOImpl pour trouver le role ===> " + role.name());
		
		try {
			TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class);
			query.setParameter("role", role.name());
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new DAOException("Erreur ClientDAO find " + e.getMessage(), e);
		}
	}


}
