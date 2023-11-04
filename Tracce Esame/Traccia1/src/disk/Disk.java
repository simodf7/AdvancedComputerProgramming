package disk;

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

public class Disk {
    
    public static void main(String[] args){

        Hashtable<String,String> properties = new Hashtable<String, String>();
        properties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory"); 
        properties.put("java.naming.provider.url", "tcp://127.0.0.1:61616"); 

        properties.put("topic.storage", "storage"); 

        try {

            Context ctx = new InitialContext(properties);
            
            TopicConnectionFactory tconnf = (TopicConnectionFactory) ctx.lookup("TopicConnectionFactory"); 

            Topic storage = (Topic) ctx.lookup("storage"); 

            TopicConnection tconn = tconnf.createTopicConnection(); 
            tconn.start();
            
            TopicSession tSession = (TopicSession) tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE); 

            TopicSubscriber sub = tSession.createSubscriber(storage); 
            Listener listener = new Listener(); 
            sub.setMessageListener(listener);




        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    




    } 


}
