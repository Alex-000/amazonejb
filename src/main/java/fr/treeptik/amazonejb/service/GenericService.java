package fr.treeptik.amazonejb.service;

import java.util.List;

import fr.treeptik.amazonejb.exception.ServiceException;

public interface GenericService<T> {
	
	// par définition d'une interface les méthodes sont public et abstract
	
		T add(T entite) throws ServiceException;
		void remove(T entite) throws ServiceException;
		T update(T entite) throws ServiceException;
		T findById(Long id) throws ServiceException;
		List<T> find(T entite) throws ServiceException;
		List<T> findAll() throws ServiceException;
	
}
