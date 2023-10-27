package proxy;


import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueSession;

import service.IServer;

public class ProxyListener implements MessageListener {


    private QueueSession qSession; 
    private Queue queue_response; 
    private IServer server; 

    public ProxyListener(QueueSession qs, Queue res, IServer s){
        qSession = qs; 
        queue_response = res; 
        server = s; 
    }


    @Override
    public void onMessage(Message message) {
        
        MapMessage msg = (MapMessage) message;

        try {
            

            String operation = msg.getString("tipo"); 
            int id = msg.getInt("id_articolo"); 

            
            System.out.println("[PROXY] Message received: " + operation + " " + id );

            if(operation.compareTo("deposita") == 0){

                
                server.deposita(id);
                System.out.println("[PROXY] Deposita Requested forwarded to server");

            }
            else if (operation.compareTo("preleva") == 0 ){


                // System.out.println("[PROXY] Message received: " + operation);
                ProxySender sender = new ProxySender(qSession, queue_response, server); 
                sender.start();
            }
            else{
                System.out.println("[PROXY] Unrecognised operation");
            }
            
        } catch (RemoteException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    


    
}
