package proxy;


import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

import service.IServer;

public class ProxySender extends Thread{
    
    private QueueSession qSession; 
    private Queue queue_response;
    private IServer server; 

    public ProxySender(QueueSession qs, Queue res, IServer s){

        qSession = qs; 
        queue_response = res; 
        server = s; 

    }


    public void run(){

        try {

        QueueSender sender  = qSession.createSender(queue_response);
        MapMessage message = qSession.createMapMessage();

        int id = server.preleva(); 
        
        message.setInt("id_articolo", id);


        sender.send(message);
        System.out.println("[PROXY] sent message: " + id );



        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        


    }


}
