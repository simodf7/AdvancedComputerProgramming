import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Dispatcher {
    public static void main(String[] args) throws Exception {
        
        Hashtable<String,String> properties = new Hashtable<String, String>(); 

        properties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory"); 
        properties.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        properties.put("queue.request", "request"); 
        properties.put("queue.response", "response"); 


        try{
            Context jndiContext = new InitialContext(properties);
            QueueConnectionFactory qconnf = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory"); 
            
            Queue request = (Queue) jndiContext.lookup("request"); 
            Queue response = (Queue) jndiContext.lookup("response"); 


            QueueConnection qconn = qconnf.createQueueConnection();
            qconn.start();

            QueueSession qSession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); 


            int port = Integer.parseInt(args[0]);
            QueueReceiver receiver = qSession.createReceiver(request); 
            DispatcherListener listener = new DispatcherListener(qSession, response, port); 
            receiver.setMessageListener(listener);


        }
        catch (NamingException e){
            e.printStackTrace();
        }
        catch(JMSException e){
            e.printStackTrace();
        }
            



        


    }
}
