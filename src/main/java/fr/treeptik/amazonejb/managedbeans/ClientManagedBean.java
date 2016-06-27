package fr.treeptik.amazonejb.managedbeans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import org.jboss.logging.Logger;

import fr.treeptik.amazonejb.model.Client;
import fr.treeptik.amazonejb.service.ClientService;

@ManagedBean
@ViewScoped
public class ClientManagedBean {

	@EJB
	public ClientService clientService;
	
	private Client client = new Client();
	
	private ListDataModel<Client> clients = new ListDataModel<>();
	
	private boolean admin;
	
	private static final Logger logger = Logger.getLogger(ClientManagedBean.class);

	
	
	public void create() {
		
		logger.info("========================================= Dans create car id ===> " + client.getId());
		
		clientService.add(client);
		
		// RequestContext.getCurrentInstance().closeDialog(0);
		
		// return "/client/client-list";
	}
	
	
	public void update() {
				
		//client = clients.getRowData();
		
		logger.info("========================================= Dans update car id ===>" + client.getId());
		
		clientService.update(client);
//		Map<String,Object> options = new HashMap<>();
//        options.put("modal", true);
//        options.put("width", 500);
//        options.put("height", 200);
//        options.put("contentWidth", "100%");
//        options.put("contentHeight", "100%");
//        options.put("headerElement", "customheader");
        
        
        //RequestContext.getCurrentInstance().openDialog("/client/client-edit.xhtml", options, null);
		
		//return "/client/client";
	}
	
	public void updateRole() {
		
		logger.info("====================== dans updateRole =================================== admin ==> " + admin + " <== pour id client ==> " + client.getId());
		
		clientService.updateRoleAdminByIdClient(admin, client.getId());
	}
	
	
	public void remove() {
		
		client = clients.getRowData();
		String nom = client.getNom();
		String prenom = client.getPrenom();
		
		clientService.remove(client);
		
		logger.info("je suis dans remove ========================================== " + nom);
		
		addMessage("Client deleted !", "Bye bye " + nom + " " + prenom);
		
		//return "/client/client-list";
	}
	
	
	
	public void addMessage(String titre, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titre,  detail);
        FacesContext.getCurrentInstance().addMessage("test", message);
    }
	
	
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}



	public ListDataModel<Client> getClients() {
		
		clients.setWrappedData(clientService.findAll());
		return clients;
	}



	public void setClients(ListDataModel<Client> clients) {
		this.clients = clients;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
}
