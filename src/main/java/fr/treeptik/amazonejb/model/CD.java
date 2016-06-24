package fr.treeptik.amazonejb.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CD extends Article {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String compositeur;
	
	private String artiste;
	
	private Integer nbChansons;
	
	@Temporal(TemporalType.TIME)
	private Date duree;

	
	
	public CD() {
	}

	public String getCompositeur() {
		return compositeur;
	}

	public void setCompositeur(String compositeur) {
		this.compositeur = compositeur;
	}

	public Integer getNbChansons() {
		return nbChansons;
	}

	public void setNbChansons(Integer nbChansons) {
		this.nbChansons = nbChansons;
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

	public String getArtiste() {
		return artiste;
	}

	public void setArtiste(String artiste) {
		this.artiste = artiste;
	}

}
