package fr.treeptik.amazonejb.dao;

import fr.treeptik.amazonejb.exception.DAOException;
import fr.treeptik.amazonejb.model.Client;

public interface ClientDAO extends GenericDAO<Client> {

	Client findWithAllById(Long id) throws DAOException;
	Client mergeAll(Client client) throws DAOException;
	
	

}
