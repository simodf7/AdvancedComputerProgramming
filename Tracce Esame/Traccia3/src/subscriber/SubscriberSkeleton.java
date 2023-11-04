package subscriber;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SubscriberSkeleton implements ISubscriber{

    private int port; 
    private ISubscriber sub;

    public SubscriberSkeleton(int p, ISubscriber s){
        port = p;
        sub = s;
    }


    public void runSkeleton(){

        try {

            ServerSocket sock = new ServerSocket(port);
            Socket client = null; 

            while(true){

                client = sock.accept(); 
                SubscriberThread thread = new SubscriberThread(client, sub); 
                thread.start();

            }





        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        

        

    }


    @Override
    public void notifyAlert(int criticality) { 
        sub.notifyAlert(criticality);
    }
    
}
