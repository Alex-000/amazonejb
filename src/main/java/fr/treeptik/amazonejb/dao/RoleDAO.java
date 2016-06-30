package fr.treeptik.amazonejb.dao;

import fr.treeptik.amazonejb.enumeration.RoleUser;
import fr.treeptik.amazonejb.model.Role;

public interface RoleDAO extends GenericDAO<Role> {

	Role findRoleByEnumRole(RoleUser role);

}
