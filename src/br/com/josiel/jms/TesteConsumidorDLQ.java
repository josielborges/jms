package br.com.josiel.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import java.lang.Exception;

public class TesteConsumidorDLQ {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception  {
		
		InitialContext context = new InitialContext();

		//imports do package javax.jms
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("DLQ");
		MessageConsumer consumer = session.createConsumer(fila);
		
		// precisa ficar esperando
//		Message message = consumer.receive();
//		System.out.println("Recebendo mensagem: " + message);
		
		// Para visualizar as mensagens sem consumi-las
//		QueueBrowser browser = session.createBrowser((Queue) fila);
//		Enumeration<?> msgs = browser.getEnumeration();
//		while (msgs.hasMoreElements()) { 
//		    TextMessage msg = (TextMessage) msgs.nextElement(); 
//		    System.out.println("Message: " + msg.getText()); 
//		}
		
		
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				System.out.println(message);
			}
		});
		
 		new Scanner(System.in).nextLine(); // para parar o programa para testes
		
 		session.close();
		connection.close();
		context.close();
		
		
	}

}
