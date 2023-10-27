package client;

import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

public class Sender extends Thread {

    private QueueSession qSession; 
    private Queue queue_request; 
    private Queue queue_response;
    private final static int N = 5;
    private final static String[] ops = {"deposita", "preleva"};


    public Sender(QueueSession qs, Queue req, Queue res){

        qSession = qs;
        queue_request = req; 
        queue_response = res; 

    }


    public void run(){


       

        try {

            QueueSender sender  = qSession.createSender(queue_request);
            MapMessage message = qSession.createMapMessage();

            for(int i=0; i<N; i++){

                Random random = new Random();
                int op = random.nextInt(2);
                System.out.println(op);
                int id = random.nextInt(100);
                
                message.setString("tipo", ops[op]);
                if(op == 0){
                    message.setInt("id_articolo", id);
                }
                
                
                

                message.setJMSReplyTo(queue_response);
                sender.send(message);
                if(op == 0){
                    System.out.println("[SENDER] sent message " + ops[op] + " " + id );
                }
                else{
                    System.out.println("[SENDER] sent message " + ops[op] );
                }
                
 

            }



        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        




        


    }



}
