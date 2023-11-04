package manager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import rider.IRider;

public class RiderProxy implements IRider{

    private int location; 
    private int port; 

    public RiderProxy(int l, int p){
        location = l;
        port = p; 
    }



    @Override
    public void notifyOrder(int id, String address) {

        try {

            Socket socket = new Socket("127.0.0.1", port);

            DataInputStream in = new DataInputStream(socket.getInputStream()); 
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeInt(id);
            out.writeUTF(address);

            socket.close();



        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }

    public int getLocation(){
        return location;
    }
    
}
