package execM;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;


import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import server.IStorage;

public class ExecM {
    
    public static void main(String[] args) {
        

        Hashtable<String, String> properties = new Hashtable<String, String>(); 

        properties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put("java.naming.provider.url", "tcp://127.0.0.1:61616"); 
        
        properties.put("topic.math", "math"); 
        properties.put("topic.text", "text"); 

        try {
            
            Context jndiContext = new InitialContext(properties);
            TopicConnectionFactory tconnf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory"); 

            Topic math = (Topic) jndiContext.lookup("math"); 

            TopicConnection tconn = tconnf.createTopicConnection();
            tconn.start();

            TopicSession tSession = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE); 

            TopicSubscriber sub = tSession.createSubscriber(math); 
            

            Registry registry = LocateRegistry.getRegistry(); 
            IStorage storage = (IStorage) registry.lookup("storage"); 
            MListener listener = new MListener(storage); 
            sub.setMessageListener(listener);





        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 


        }


}
