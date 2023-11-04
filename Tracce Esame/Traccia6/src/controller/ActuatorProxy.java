package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import actuator.IActuator;

public class ActuatorProxy implements IActuator{

    private int port; 

    public ActuatorProxy(int p) {
        port = p;
    }

    @Override
    public boolean execute(String readingAttrs) {

        boolean check = false; 
        Socket socket; 

        try {
            socket = new Socket("127.0.0.1", port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            DataInputStream in = new DataInputStream(socket.getInputStream()); 


            out.writeUTF(readingAttrs);
            check = in.readBoolean(); 

            socket.close();
            

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return check;


        

    }

}
