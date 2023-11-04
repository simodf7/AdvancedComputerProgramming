import java.util.Hashtable;
import java.util.Random;

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

public class Client {

    public static void main(String[] args) {
        
        Hashtable<String, String> properties = new Hashtable<String, String>(); 
        properties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory"); 
        properties.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        properties.put("topic.data", "data");

        try {
            Context jndiContext = new InitialContext(properties);
            TopicConnectionFactory tconnf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic data = (Topic) jndiContext.lookup("data");
            // Topic temp = (Topic) jndiContext.lookup("temp");


            TopicConnection tconn = tconnf.createTopicConnection();
            tconn.start();

            TopicSession tSession = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicPublisher publisher = tSession.createPublisher(data);

            MapMessage message = tSession.createMapMessage();
            Random random = new Random();
            String type = args[0];
            int value = 0; 

            message.setString("type", type);

            for(int i=0; i<20; i++){
                
                if(type.compareTo("temperature") == 0 ){
                    value = random.nextInt(101);
                    message.setInt("value",  value);
                }   
                else{
                    value = random.nextInt(51) + 1000;
                    message.setInt("value",  value);
                }   

                System.out.println("[CLIENT] Sent message type: " + type + " value: " + value);
                publisher.send(message);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        



    }
    
}