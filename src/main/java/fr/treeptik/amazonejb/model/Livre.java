package fr.treeptik.amazonejb.model;

import javax.persistence.Entity;

@Entity
public class Livre extends Article {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String auteur;
	
	private Long nbPages;
	
	private Long isbn;

	
	
	public Livre() {
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public Long getNbPages() {
		return nbPages;
	}

	public void setNbPages(Long nbPages) {
		this.nbPages = nbPages;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
