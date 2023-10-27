package Sensor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.management.JMException;

import Coda.ICoda;

public class SensorListener implements MessageListener{

    private ICoda coda;

    public SensorListener(ICoda c){
        coda = c;
    }

    @Override
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        TextMessage msg = (TextMessage) message;
        

        try {
            SensorManager manager = new SensorManager(coda, msg.getText());
            manager.start();
            System.out.println("[SUBSCRIBER_LISTENER] Message received: " + msg.getText());

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
    
}
