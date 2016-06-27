package fr.treeptik.amazonejb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Commande implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Transient
	private Double total;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCommande;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLivraison;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Article> articles;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Client client;

	
	
	public Commande() {
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Double getTotal() {
		return total;
	}



	public void setTotal(Double total) {
		this.total = total;
	}



	public Date getDateCommande() {
		return dateCommande;
	}



	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}



	public Date getDateLivraison() {
		return dateLivraison;
	}



	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}



	public List<Article> getArticles() {
		return articles;
	}



	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}



	public Client getClient() {
		return client;
	}



	public void setClient(Client client) {
		this.client = client;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
