package fr.treeptik.amazonejb.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DVD extends Article {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String auteur;
	
	private String[] acteurs;
	
	@Temporal(TemporalType.TIME)
	private Date duree;

	
	
	public DVD() {
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String[] getActeurs() {
		return acteurs;
	}

	public void setActeurs(String[] acteurs) {
		this.acteurs = acteurs;
	}

	public Date getDuree() {
		return duree;
	}

	public void setDuree(Date duree) {
		this.duree = duree;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
