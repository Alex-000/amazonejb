package fr.treeptik.amazonejb.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.logging.Logger;

@MessageDriven(activationConfig = {
		
		@ActivationConfigProperty(propertyName =  "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName =  "destination", propertyValue = "java:/jms/AmazonQueue")
})
public class ClientMessageListener implements MessageListener {

	private static final Logger logger = Logger.getLogger(ClientMessageListener.class);
	
	@Override
	public void onMessage(Message message) {
		
		ObjectMessage msg = (ObjectMessage) message;
		
		try {
			logger.info(msg.getObject());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
