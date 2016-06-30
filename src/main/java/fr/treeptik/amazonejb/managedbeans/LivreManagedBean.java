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

import fr.treeptik.amazonejb.model.Livre;
import fr.treeptik.amazonejb.service.LivreService;

@ManagedBean
@ViewScoped
public class LivreManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	public LivreService livreService;
	
	private Livre livre = new Livre();
	
	private ListDataModel<Livre> livres = new ListDataModel<>();
	
	private static final Logger logger = Logger.getLogger(LivreManagedBean.class);

	
	
	@PostConstruct
	public void init() {
		
		logger.info("\n========================================= Dans init ===> PostConstruct\n");
		
		livres.setWrappedData(livreService.findAll());
	}

	public void resetLivre() {
		
		logger.info("\n========================================= Dans resetLivre\n");
		
		livre = new Livre();
	}
	
	
	
	public void create() {
		
		logger.info("\n========================================= Dans create car id ===> " + livre.getId() + "\n");
		
		livre = livreService.add(livre);
		
		addMessage("Création Livre", livre.getTitre());
		
		// on remet à jour notre liste de livres
		livres.setWrappedData(livreService.findAll());
	}
	
	
	public void update() {
		
		logger.info("\n========================================= Dans update car id ===>" + livre.getId() + "\n");
		
		livreService.update(livre);
		
		addMessage("Modification Livre" + livre.getTitre(), "Refresh la page pour le voir !");
		
		// on remet à jour notre liste de livres
		livres.setWrappedData(livreService.findAll());
	}
	
	public void remove() {
		
		logger.info("\n========================================== je suis dans remove \n");

		livre = livres.getRowData();
		
		String titre = livre.getTitre();
		
		livreService.remove(livre);
		
		
		addMessage("Suppression Livre !", "Bye bye " + titre);
		
		// on remet à jour notre liste de livres
		livres.setWrappedData(livreService.findAll());
	}
	
	
	
	public void addMessage(String titre, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titre,  detail);
        FacesContext.getCurrentInstance().addMessage("test", message);
    }





	public LivreService getlivreService() {
		return livreService;
	}





	public void setlivreService(LivreService livreService) {
		this.livreService = livreService;
	}





	public Livre getlivre() {
		return livre;
	}





	public void setlivre(Livre livre) {
		this.livre = livre;
	}





	public ListDataModel<Livre> getlivres() {
		return livres;
	}





	public void setlivres(ListDataModel<Livre> livres) {
		this.livres = livres;
	}
	
}
