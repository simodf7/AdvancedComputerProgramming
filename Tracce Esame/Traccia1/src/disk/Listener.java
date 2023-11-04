package disk;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class Listener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        
        MapMessage msg = (MapMessage) message; 
        try {
            int dato = msg.getInt("dato"); 
            int porta = msg.getInt("porta"); 

            System.out.println("[LISTENER] Message received: " + dato + " " + porta);

            DiskThread worker = new DiskThread(dato, porta);
            worker.start(); 

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
