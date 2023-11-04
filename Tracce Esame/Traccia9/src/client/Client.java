package client;

import java.util.Hashtable;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
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
        
        properties.put("topic.math", "math"); 
        properties.put("topic.text", "text"); 

        try {
            
            Context jndiContext = new InitialContext(properties);
            TopicConnectionFactory tconnf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory"); 

            Topic math = (Topic) jndiContext.lookup("math"); 
            Topic text = (Topic) jndiContext.lookup("text");

            TopicConnection tconn = tconnf.createTopicConnection();
            tconn.start();

            TopicSession tSession = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE); 

            String type = args[0]; 

            Random random = new Random(); 

            if(type.compareTo("math") == 0){

                TopicPublisher pub = tSession.createPublisher(math); 
                MapMessage message = tSession.createMapMessage();

                for(int i=0; i<5; i++){
                    
                    int param1 = random.nextInt(101); 
                    int param2 = random.nextInt(101); 

                    message.setInt("param1", param1);
                    message.setInt("param2", param2);

                    pub.send(message);
                    System.out.println("[CLIENT] Sent message with params: " + param1 + " " + param2 + " to math topic");

                    Thread.sleep(2000);
                }
                
                
                
            }
            else if(type.compareTo("text") == 0) {

                TopicPublisher pub = tSession.createPublisher(text); 
                TextMessage message = tSession.createTextMessage();
                String method = "save#"; 

                for(int i=0; i<5; i++){
                    int param1 = random.nextInt(101); 

                    message.setText(method + param1);

                    pub.send(message);
                    System.out.println("[CLIENT] Sent message with string: "+  (method + param1) + " to text topic");
                }
                
                Thread.sleep(2000);
                
            }
            else{
                System.out.println("Argument invalid");
            }





        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 


    }


}