package subscriber;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SubscriberThread  extends Thread{
    
    private Socket socket; 
    private ISubscriber sub; 


    public SubscriberThread(Socket sock, ISubscriber s){

        socket = sock; 
        sub = s; 

    }

    public void run(){

        System.out.println("[SUBSCRIBER] Thread created");

        try {

    
            DataInputStream in = new DataInputStream(socket.getInputStream());

            int criticality = in.readInt();
            sub.notifyAlert(criticality);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }



}
