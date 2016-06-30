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

import fr.treeptik.amazonejb.model.CD;
import fr.treeptik.amazonejb.service.CDService;

@ManagedBean
@ViewScoped
public class CDManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	public CDService cdService;
	
	private CD cd = new CD();
	
	private ListDataModel<CD> cds = new ListDataModel<>();
	
	private static final Logger logger = Logger.getLogger(CDManagedBean.class);

	
	
	@PostConstruct
	public void init() {
		
		logger.info("\n========================================= Dans init ===> PostConstruct\n");
		
		cds.setWrappedData(cdService.findAll());
	}

	public void resetCd() {
		
		logger.info("\n========================================= Dans resetCD\n");
		
		cd = new CD();
	}
	
	
	
	public void create() {
		
		logger.info("\n========================================= Dans create car id ===> " + cd.getId() + "\n");
		
		cd = cdService.add(cd);
		
		addMessage("Création CD" + cd.getTitre(), "Refresh la page pour le voir !");
		
		// on remet à jour notre liste de cds
		cds.setWrappedData(cdService.findAll());
	}
	
	
	public void update() {
		
		logger.info("\n========================================= Dans update car id ===>" + cd.getId() + "\n");
		
		cdService.update(cd);
		
		addMessage("Modification CD", cd.getTitre());
		
		// on remet à jour notre liste de cds
		cds.setWrappedData(cdService.findAll());
	}
	
	public void remove() {
		
		logger.info("\n========================================== je suis dans remove \n");

		cd = cds.getRowData();
		
		String titre = cd.getTitre();
		
		cdService.remove(cd);
		
		
		addMessage("Suppression CD !", "Bye bye " + titre);
		
		// on remet à jour notre liste de cds
		cds.setWrappedData(cdService.findAll());
	}
	
	
	
	public void addMessage(String titre, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titre,  detail);
        FacesContext.getCurrentInstance().addMessage("test", message);
    }





	public CDService getcdService() {
		return cdService;
	}





	public void setcdService(CDService cdService) {
		this.cdService = cdService;
	}





	public CD getcd() {
		return cd;
	}





	public void setcd(CD cd) {
		this.cd = cd;
	}





	public ListDataModel<CD> getcds() {
		return cds;
	}





	public void setcds(ListDataModel<CD> cds) {
		this.cds = cds;
	}
	
}
