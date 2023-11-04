package rider;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class RiderThread extends Thread {

    private Socket sock;
    private IRider rider;


    public RiderThread(Socket s, IRider r){
        sock = s;
        rider = r;
    }

    public void run(){

        try {
            
            DataInputStream in = new DataInputStream(sock.getInputStream());
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());

            int id = in.readInt();
            String address = in.readUTF();
            
            System.out.println("[RIDER THREAD] id: " + id + " address: "+ address );

            rider.notifyOrder(id, address);

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        



    }

}
