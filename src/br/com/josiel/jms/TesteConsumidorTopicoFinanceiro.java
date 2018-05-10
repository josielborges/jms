package br.com.josiel.jms;

import java.util.Scanner;

import java.lang.Exception;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class TesteConsumidorTopicoFinanceiro {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();

		//imports do package javax.jms
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		connection.setClientID("Financeiro");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topico = (Topic) context.lookup("exemploJosielTopico");
		MessageConsumer consumer = session.createDurableSubscriber(topico, "Assinatura");
		
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
				TextMessage texto = (TextMessage) message;
				try {
					System.out.println(texto.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
 		new Scanner(System.in).nextLine(); // para parar o programa para testes
		
 		session.close();
		connection.close();
		context.close();
		
		
	}

}
