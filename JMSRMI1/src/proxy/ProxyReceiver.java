package proxy;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;

import service.IServer;

public class ProxyReceiver extends Thread{
    
    private QueueSession qSession; 
    private Queue queue_request;
    private Queue queue_response; 
    private IServer server; 


    public ProxyReceiver(QueueSession qs, Queue req,  Queue res, IServer s){
        qSession = qs; 
        queue_request = req;
        queue_response = res; 
        server = s; 
    }

    public void run(){

        try {

            QueueReceiver receiver = qSession.createReceiver(queue_request);
            ProxyListener listener = new ProxyListener(qSession, queue_response, server); 
            receiver.setMessageListener(listener);
            System.out.println("[PROXY] Listening... ");


        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       


    }





}
