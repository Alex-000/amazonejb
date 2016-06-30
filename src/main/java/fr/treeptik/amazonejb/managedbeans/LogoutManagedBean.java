package fr.treeptik.amazonejb.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

@ManagedBean
@RequestScoped
public class LogoutManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(LogoutManagedBean.class);
	
	public String logout() {
		
		logger.info("================================================================= logout");
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
        
	}
}
