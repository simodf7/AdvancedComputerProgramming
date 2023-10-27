package dispatcher;

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

public class Dispatcher {
    
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


        TopicSubscriber sub = tSession.createSubscriber(printRequest);
        Listener listener = new Listener(); 
        
        sub.setMessageListener(listener);

    }


}
