package fr.treeptik.amazonejb.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class LogoutManagedBean {

	public String logout() {
		
		System.out.println("================================================================= logout");
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
        
	}
}
