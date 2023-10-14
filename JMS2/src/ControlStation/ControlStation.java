package ControlStation;

import java.util.Hashtable;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class ControlStation {

    private static final int N = 20;
    private static final String[] cmds = {"startSensor", "stopSensor", "read"};
    
    public static void main(String args[]){

        Hashtable <String, String> p = new Hashtable<String, String>();

        p.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url", "tcp://127.0.0.1:61616");
        p.put("topic.commands", "commands");

        try{
            Context ctx = new InitialContext(p);
            TopicConnectionFactory tconnf =  (TopicConnectionFactory) ctx.lookup("TopicConnectionFactory"); 

            Topic topic_command = (Topic) ctx.lookup("commands");

            TopicConnection tconn = tconnf.createTopicConnection();
            tconn.start();

            TopicSession tsession = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicPublisher publisher = tsession.createPublisher(topic_command);


            for(int i=0; i<N; i++){

                Random random = new Random();
                String cmd = cmds[random.nextInt(3)];
                TextMessage m = tsession.createTextMessage(cmd);
                publisher.publish(m);
                System.out.println("[PUBLISHER] Published message: " + cmd );
            }

            publisher.close();
            tsession.close();
            tconn.close();

        } catch(Exception e){
            e.printStackTrace();
        }        


    }


}
