package br.com.josiel.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();

		//imports do package javax.jms
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("exemploJosiel");
		
		MessageProducer producer = session.createProducer(fila);
		
	    for (int i = 0; i < 1000; i++) {
	    	Message message = session.createTextMessage("Teste " + i);
	    	producer.send(message);
		}
	    
 		session.close();
		connection.close();
		context.close();
		
		
	}

}
