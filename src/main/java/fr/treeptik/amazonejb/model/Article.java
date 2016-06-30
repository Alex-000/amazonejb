package fr.treeptik.amazonejb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// si on veut préciser le nom et le type de la colonne DTYPE, normalement c'est mieux
//@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titre;
	
	private Double prix;
	
	private String genre;
	
	private Long stock;
	
	@ManyToMany(mappedBy = "articles", fetch = FetchType.LAZY)
	private List<Commande> commandes;
	
	@Column(nullable = false)
	private boolean deleted;
	
	// permet d'avoir acces à DTYPE, par contre on ne le gere pas avec insert et update
	@Column(name = "DTYPE", insertable = false, updatable = false)
	private String dtype;

	
	
	public Article() {
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitre() {
		return titre;
	}



	public void setTitre(String titre) {
		this.titre = titre;
	}



	public Double getPrix() {
		return prix;
	}



	public void setPrix(Double prix) {
		this.prix = prix;
	}



	public String getGenre() {
		return genre;
	}



	public void setGenre(String genre) {
		this.genre = genre;
	}



	public List<Commande> getCommandes() {
		return commandes;
	}



	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Long getStock() {
		return stock;
	}



	public void setStock(Long stock) {
		this.stock = stock;
	}



	public boolean isDeleted() {
		return deleted;
	}



	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}




	public String getDtype() {
		return dtype;
	}



	public void setDtype(String dtype) {
		this.dtype = dtype;
	}



	@Override
	public String toString() {
		return "Article [id=" + id + ", titre=" + titre + ", prix=" + prix + ", genre=" + genre + ", stock=" + stock
				+ ", deleted=" + deleted + ", dtype=" + dtype + "]";
	}



}
