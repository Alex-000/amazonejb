package fr.treeptik.amazonejb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import fr.treeptik.amazonejb.enumeration.RoleUser;

@Entity
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	private String prenom;
	
	private String login;
	
	private String password;
	
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	
	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	private List<Commande> commandes;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Role> roles;
	
	@Transient
	private List<Role> rolesToDisplay;
	
	
	
	public Client() {
		// constructeur vide
	}
	
	
	public boolean hasRole(RoleUser role) {
		
		for (Role r : roles) {
			if (r.getRole().equals(role.name())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasRole(String role) {
		
		for (Role r : roles) {
			if (r.getRole().equals(role)) {
				return true;
			}
		}
		return false;
	}
	
	public Role getRole(RoleUser role) {
		
		for (Role r : roles) {
			if (r.getRole().equals(role.name())) {
				return r;
			}
		}
		return null;
	}
	
	public void addRole(Role role) {
		
		if (this.roles == null) {
			this.roles = new HashSet<>();
		}
		if (!hasRole(role.getRole())) {
			this.roles.add(role);
		}
		
	}
	
	public void removeRole(Role role) {
		
		if (this.roles != null) {
			this.roles.remove(role);
		}
	}
	
	public void removeRole(RoleUser role) {
		
		if (this.roles != null && !this.roles.isEmpty()) {
			this.roles.remove(getRole(role));
		}
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Date getDateNaissance() {
		return dateNaissance;
	}


	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	public List<Commande> getCommandes() {
		return commandes;
	}


	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public List<Role> getRolesToDisplay() {
		if (this.roles != null) {
			this.rolesToDisplay = new ArrayList<>(this.roles);
		}
		return this.rolesToDisplay;
	}


	public void setRolesToDisplay(List<Role> rolesToDisplay) {
		this.rolesToDisplay = rolesToDisplay;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", login=" + login + ", password="
				+ password + ", dateNaissance=" + dateNaissance + "]";
	}


	
}
