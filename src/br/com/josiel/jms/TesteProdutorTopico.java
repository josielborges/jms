package br.com.josiel.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.josiel.modelo.Pedido;
import br.com.josiel.modelo.PedidoFactory;

public class TesteProdutorTopico {

	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();

		//imports do package javax.jms
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topico = (Destination) context.lookup("exemploJosielTopico");
        MessageProducer producer = session.createProducer(topico);
		
//        TextMessage eboookMessage = session.createTextMessage("Teste Ebook");
//        eboookMessage.setBooleanProperty("ebook", true);
//        
//        TextMessage livroFisicoMessage = session.createTextMessage("Teste Livro Fisico");
//        livroFisicoMessage.setBooleanProperty("ebook", false);
//        
//        TextMessage livroNulo = session.createTextMessage("Teste Livro Nulo");
//        
//    	producer.send(eboookMessage);
//    	producer.send(livroFisicoMessage);
//    	producer.send(livroNulo);
    	
        Pedido pedido = new PedidoFactory().geraPedidoComValores();
        
    	ObjectMessage objectMessage = session.createObjectMessage(pedido);
    	producer.send(objectMessage);
    	
    	session.close();
		connection.close();
		context.close();
		
		
	}

}
