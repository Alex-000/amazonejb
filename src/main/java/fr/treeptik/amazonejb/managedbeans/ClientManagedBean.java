package fr.treeptik.amazonejb.managedbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import org.primefaces.context.RequestContext;

import fr.treeptik.amazonejb.model.Client;
import fr.treeptik.amazonejb.service.ClientService;

@ManagedBean
@RequestScoped
public class ClientManagedBean {

	@EJB
	public ClientService clientService;
	
	private Client client = new Client();
	
	private ListDataModel<Client> clients = new ListDataModel<>();

	
	
	public String create() {
		
		clientService.add(client);
		
		RequestContext.getCurrentInstance().closeDialog(0);
		
		return "/client/client-list";
	}
	
	
	public void update() {
				
		//client = clients.getRowData();
		
		System.out.println("je suis dans update ========================================= " + client.getId());
		
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
	
	
	public void remove() {
		
		client = clients.getRowData();
		String nom = client.getNom();
		
		clientService.remove(client);
		
		System.out.println("je suis dans remove ========================================= " + nom);
		addMessage("Client " + nom + " deleted !");
		
		//return "/client/client-list";
	}
	
	
	
	public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
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
	
}
