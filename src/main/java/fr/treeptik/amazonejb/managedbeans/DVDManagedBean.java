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

import fr.treeptik.amazonejb.model.DVD;
import fr.treeptik.amazonejb.service.DVDService;

@ManagedBean
@ViewScoped
public class DVDManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	public DVDService dvdService;
	
	private DVD dvd = new DVD();
	
	private ListDataModel<DVD> dvds = new ListDataModel<>();
	
	private static final Logger logger = Logger.getLogger(DVDManagedBean.class);

	
	
	@PostConstruct
	public void init() {
		
		logger.info("\n========================================= Dans init ===> PostConstruct\n");
		
		dvds.setWrappedData(dvdService.findAll());
	}

	public void resetDvd() {
		
		logger.info("\n========================================= Dans resetDVD\n");
		
		dvd = new DVD();
	}
	
	
	
	public void create() {
		
		logger.info("\n========================================= Dans create car id ===> " + dvd.getId() + "\n");
		
		dvd = dvdService.add(dvd);
		
		addMessage("Création DVD" + dvd.getTitre(), "Refresh la page pour le voir !");
		
		// on remet à jour notre liste de dvds
		dvds.setWrappedData(dvdService.findAll());
	}
	
	
	public void update() {
		
		logger.info("\n========================================= Dans update car id ===>" + dvd.getId() + "\n");
		
		dvdService.update(dvd);
		
		addMessage("Modification DVD", dvd.getTitre());
		
		// on remet à jour notre liste de dvds
		dvds.setWrappedData(dvdService.findAll());
	}
	
	public void remove() {
		
		logger.info("\n========================================== je suis dans remove \n");

		dvd = dvds.getRowData();
		
		String titre = dvd.getTitre();
		
		dvdService.remove(dvd);
		
		
		addMessage("Suppression DVD !", "Bye bye " + titre);
		
		// on remet à jour notre liste de dvds
		dvds.setWrappedData(dvdService.findAll());
	}
	
	
	
	public void addMessage(String titre, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titre,  detail);
        FacesContext.getCurrentInstance().addMessage("test", message);
    }





	public DVDService getdvdService() {
		return dvdService;
	}





	public void setdvdService(DVDService dvdService) {
		this.dvdService = dvdService;
	}





	public DVD getdvd() {
		return dvd;
	}





	public void setdvd(DVD dvd) {
		this.dvd = dvd;
	}





	public ListDataModel<DVD> getdvds() {
		return dvds;
	}





	public void setdvds(ListDataModel<DVD> dvds) {
		this.dvds = dvds;
	}
	
}
