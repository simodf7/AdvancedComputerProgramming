package client;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class Listener implements MessageListener{

    @Override
    public void onMessage(Message message) {

        MapMessage msg = (MapMessage) message; 

        try {
            System.out.println("[LISTENER] Message received: " + msg.getInt("id_articolo") );
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }
    


    
}
