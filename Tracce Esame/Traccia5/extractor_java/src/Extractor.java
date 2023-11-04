import java.util.Hashtable;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Extractor {
    
    public static void main(String[] args) {
        
        Hashtable<String, String> properties = new Hashtable<String, String>(); 
        properties.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory"); 
        properties.put("java.naming.provider.url", "tcp://127.0.0.1:61616");

        properties.put("topic.data", "data");
        properties.put("topic.temp", "temp");
        properties.put("topic.press", "press");

        try {
            Context jndiContext = new InitialContext(properties);
            TopicConnectionFactory tconnf = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
            Topic data = (Topic) jndiContext.lookup("data");
            Topic temp = (Topic) jndiContext.lookup("temp");
            Topic press = (Topic) jndiContext.lookup("press");

            TopicConnection tconn = tconnf.createTopicConnection();
            tconn.start();

            TopicSession tSession = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber subscriber = tSession.createSubscriber(data);
            TopicPublisher temp_pub = tSession.createPublisher(temp);
            TopicPublisher press_pub = tSession.createPublisher(press);
            TextMessage message = tSession.createTextMessage();


            ExtractorListener listener = new ExtractorListener(temp_pub, press_pub, message); 
            subscriber.setMessageListener(listener);


        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        



    }
}
