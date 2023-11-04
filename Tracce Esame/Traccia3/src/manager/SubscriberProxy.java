package manager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import subscriber.ISubscriber;

public class SubscriberProxy implements ISubscriber{

    private int componentId; 
    private int port; 

    public SubscriberProxy(int c, int p){
        componentId = c; 
        port = p;
    }

    public int getId(){
        return componentId;
    }

    public int port(){
        return port; 
    }


    @Override
    public void notifyAlert(int criticality) {
       
        try {

            Socket socket = new Socket("127.0.0.1", port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(criticality);

            socket.close();


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 


    }
    
}
