package fr.treeptik.amazonejb.dao;

import java.util.List;

import fr.treeptik.amazonejb.exception.DAOException;

public interface GenericDAO<T> {

	// par définition d'une interface les méthodes sont public et abstract
	
	T save(T entite) throws DAOException;
	void remove(T entite) throws DAOException;
	T findById(Long id) throws DAOException;
	List<T> find(T entite) throws DAOException;
	List<T> findAll() throws DAOException;
	
}
