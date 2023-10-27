package client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;

public class Client {
    
    public static void main(String[] args) throws NamingException, JMSException{

        Hashtable <String, String> p = new Hashtable<String, String>();

        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("queue.richiesta", "richiesta");
        p.put("queue.risposta", "risposta");


        Context ctx = new InitialContext(p);

        QueueConnectionFactory qconnf = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");

        Queue queue_request = (Queue) ctx.lookup("richiesta");
        Queue queue_response = (Queue) ctx.lookup("risposta");


        QueueConnection qconn = qconnf.createQueueConnection();
        qconn.start();

        QueueSession qSession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);


        Receiver receiver = new Receiver(qSession, queue_response);
        receiver.start();

        Sender sender = new Sender(qSession, queue_request, queue_response);
        sender.start();




    }



}
