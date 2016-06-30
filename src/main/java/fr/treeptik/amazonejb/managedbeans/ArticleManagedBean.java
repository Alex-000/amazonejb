package fr.treeptik.amazonejb.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import org.jboss.logging.Logger;

import fr.treeptik.amazonejb.model.Article;
import fr.treeptik.amazonejb.service.ArticleService;

@ManagedBean
@ViewScoped
public class ArticleManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	public ArticleService articleService;
	
	private Article article = new Article();
	
	private ListDataModel<Article> articles = new ListDataModel<>();
	
	private static final Logger logger = Logger.getLogger(ArticleManagedBean.class);

	
	
	@PostConstruct
	public void init() {
		
		logger.info("\n========================================= Dans init ===> PostConstruct\n");
		
		articles.setWrappedData(articleService.findAll());
	}

	public void resetArticle() {
		
		logger.info("\n========================================= Dans resetArticle\n");
		
		article = new Article();
	}
	
	
	
	public void create() {
		
		logger.info("\n========================================= Dans create car id ===> " + article.getId() + "\n");
		
		article = articleService.add(article);
		
		addMessage("Création Article" + article.getTitre(), "Refresh la page pour le voir !");
		
		// on remet à jour notre liste de articles
		articles.setWrappedData(articleService.findAll());
	}
	
	
	public void update() {
		
		logger.info("\n========================================= Dans update car id ===>" + article.getId() + "\n");
		
		articleService.update(article);
		
		addMessage("Modification Article", article.getTitre());
		
		// on remet à jour notre liste de articles
		articles.setWrappedData(articleService.findAll());
	}
	
	public void remove() {
		
		logger.info("\n========================================== je suis dans remove \n");

		article = articles.getRowData();
		
		String titre = article.getTitre();
		
		articleService.remove(article);
		
		
		addMessage("Suppression Article !", "Bye bye " + titre);
		
		// on remet à jour notre liste de articles
		articles.setWrappedData(articleService.findAll());
	}
	
	
	
	public void addMessage(String titre, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titre,  detail);
        FacesContext.getCurrentInstance().addMessage("test", message);
    }





	public ArticleService getArticleService() {
		return articleService;
	}





	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}





	public Article getArticle() {
		return article;
	}





	public void setArticle(Article article) {
		this.article = article;
	}





	public ListDataModel<Article> getArticles() {
		return articles;
	}





	public void setArticles(ListDataModel<Article> articles) {
		this.articles = articles;
	}
	
}
