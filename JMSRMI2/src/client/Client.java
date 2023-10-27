package client;

import java.util.Hashtable;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Client {
    

    public static void main(String[] args) throws NamingException, JMSException{

        Hashtable <String, String> properties = new Hashtable<String, String>();
        properties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put("java.naming.providerl.url", "tcp://127.0.0.1:61616");

        properties.put("topic.PrintRequest", "PrintRequest");

        Context jndiContext = new InitialContext(properties); 

        TopicConnectionFactory tconnf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");

        Topic printRequest = (Topic) jndiContext.lookup("PrintRequest");

        TopicConnection tconn = tconnf.createTopicConnection();
        tconn.start();

        TopicSession tSession = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);


        TopicPublisher pub = tSession.createPublisher(printRequest);

        MapMessage message = tSession.createMapMessage();

        message.setString("nomePrinter",args[0]);
            

        for(int i=0; i<5; i++){

            Random random = new Random();
            int num = random.nextInt(41);
            String document = "doc" + num;


            message.setString("nomeDocumento", document);
            
            pub.send(message);
            System.out.println("[CLIENT] Message sent to topic: " + document + " " + args[0]);


        }



    }

    




}
