package fr.treeptik.amazonejb.service;

import fr.treeptik.amazonejb.model.Client;

public interface ClientService extends GenericService<Client> {

	Client updateRoleAdminByIdClient(boolean admin, Long id);

}
