
import java.util.Hashtable;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Dispatcher {
    public static void main(String[] args) throws Exception {
        
        Hashtable<String,String> properties = new Hashtable<String, String>(); 
        properties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put("java.naming.provider.url", "tcp://127.0.0.1:61616" ); 

        properties.put("queue.request", "request");
        properties.put("queue.response", "response"); 

        Context jndiContext = new InitialContext(properties); 
        QueueConnectionFactory qconnf = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory"); 

        QueueConnection qconn = qconnf.createQueueConnection(); 
        qconn.start();

        Queue request = (Queue) jndiContext.lookup("request"); 
        Queue response = (Queue) jndiContext.lookup("response"); 


        QueueSession qSession = qconn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE); 

        QueueReceiver receiver = qSession.createReceiver(request); 

        int port = Integer.parseInt(args[0]); 

        DispatcherListener listener = new DispatcherListener(qSession, response, port); 
        receiver.setMessageListener(listener);








    }
}
