package Sensor;

import java.util.Hashtable;

import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

import Coda.CodaImpl;
import Coda.CodaWrapperLock;

import javax.jms.Session;


public class Sensor {

    private static final int D = 5;     
    
    public static void main(String[] args){

        CodaImpl coda = new CodaImpl(D);
        CodaWrapperLock wrapper = new CodaWrapperLock(coda);


        Hashtable <String, String> p = new Hashtable<String, String>();
        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        p.put("topic.commands", "commands");

        try{
            Context ctx = new InitialContext(p);
            TopicConnectionFactory tconnf = (TopicConnectionFactory) ctx.lookup("TopicConnectionFactory"); 

            Topic topic_command = (Topic) ctx.lookup("commands");

            TopicConnection tconn = tconnf.createTopicConnection();
            


            TopicSession tsession = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicSubscriber subscriber = tsession.createSubscriber(topic_command);
            SensorListener listener = new SensorListener(wrapper);
            subscriber.setMessageListener(listener);

            tconn.start();
            System.out.println("Sensore in ascolto");

            SensorExecutor executor = new SensorExecutor(wrapper);
            executor.start();
 

        } catch (Exception e){
            e.printStackTrace();
        }
       







    }



}
