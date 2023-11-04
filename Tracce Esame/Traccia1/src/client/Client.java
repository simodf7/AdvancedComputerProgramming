package client;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Client{


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
            
            TopicSession tSession = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE); 

            TopicPublisher pub = tSession.createPublisher(storage); 

            MapMessage message = tSession.createMapMessage(); 

            message.setInt("dato", Integer.valueOf(args[0]));
            message.setInt("porta", Integer.valueOf(args[1]));


            pub.send(message);




        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        



    }


}