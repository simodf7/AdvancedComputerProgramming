package rider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RiderSkeleton implements IRider {

    private IRider rider; 
    private int port; 

    public RiderSkeleton(IRider r, int p){
        rider = r; 
        port = p;
    }

    public void runSkeleton(){


        ServerSocket sock;
        try {
            
            sock = new ServerSocket(port);
            while (true) {
                Socket client = sock.accept();
                RiderThread thread = new RiderThread(client, this); 
                thread.start();
            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

       



    }

    @Override
    public void notifyOrder(int id, String address) {
        rider.notifyOrder(id, address);
    }
    
}
