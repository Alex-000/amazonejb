package fr.treeptik.amazonejb.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;

import org.jboss.logging.Logger;

import fr.treeptik.amazonejb.model.Client;
import fr.treeptik.amazonejb.service.ClientService;

@ManagedBean
@ViewScoped
public class ClientManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	public ClientService clientService;
	
	private Client client = new Client();
	
	private ListDataModel<Client> clients = new ListDataModel<>();
	
	private boolean admin;
	
	private static final Logger logger = Logger.getLogger(ClientManagedBean.class);

	
	
	@PostConstruct
	public void init() {
		logger.info("\n========================================= Dans init ===> PostConstruct\n");
		clients.setWrappedData(clientService.findAll());
	}

	
	
	public void resetClient() {
		client = new Client();
	}
	
	public void create() {
		
		logger.info("\n========================================= Dans create car id ===> " + client.getId() + "\n");
		
		clientService.add(client);
		
		// on remet à jour notre liste de clients
		clients.setWrappedData(clientService.findAll());
		
		// RequestContext.getCurrentInstance().closeDialog(0);
		
		// return "/client/client-list";
	}
	
	
	public void update() {
				
		//client = clients.getRowData();
		
		logger.info("\n========================================= Dans update car id ===>" + client.getId() + "\n");
		
		clientService.update(client);
		
		// on remet à jour notre liste de clients
		clients.setWrappedData(clientService.findAll());
				
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
	
	public void updateRole(ActionEvent event) {
		
		System.out.println(event.getComponent().getChildCount());
		System.out.println(event.getComponent().getClientId());
		System.out.println(event.getComponent().getFacetCount());
		System.out.println(event.getComponent().getFamily());
		System.out.println(event.getComponent().getId());
		System.out.println(event.getComponent().getRendererType());
		System.out.println(event.getComponent().getRendersChildren());
		System.out.println(event.getComponent().getChildren());
		System.out.println(event.getComponent().getClass());
		System.out.println(event.getComponent().isInView());
		System.out.println(event.getComponent().initialStateMarked());
		System.out.println(event.getComponent().isRendered());
		logger.info("\n====================== dans updateRole =================================== admin ==> " + admin + " <== pour id client ==> " + client.getId() + "\n");
		
		clientService.updateRoleAdminByIdClient(admin, client.getId());
		
		// on remet à jour notre liste de clients
		clients.setWrappedData(clientService.findAll());
	}
	
	
	public void remove() {
		
		logger.info("\n========================================== je suis dans remove \n");

		client = clients.getRowData();
		String nom = client.getNom();
		String prenom = client.getPrenom();
		
		clientService.remove(client);
		
		
		addMessage("Client deleted !", "Bye bye " + nom + " " + prenom);
		
		// on remet à jour notre liste de clients
		clients.setWrappedData(clientService.findAll());
		
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
