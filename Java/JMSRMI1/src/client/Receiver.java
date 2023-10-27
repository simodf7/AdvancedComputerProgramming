package client;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;

public class Receiver extends Thread {
    
    private QueueSession qSession; 
    private Queue queue_response;

    public Receiver(QueueSession qs, Queue res){

        qSession = qs;
        queue_response = res; 

    }
        


    public void run(){

        try {
            
            QueueReceiver receiver = qSession.createReceiver(queue_response);
            Listener listener = new Listener();
            receiver.setMessageListener(listener);


        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    





        
    }


}
