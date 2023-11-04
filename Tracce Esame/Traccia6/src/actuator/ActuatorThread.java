package actuator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ActuatorThread extends Thread{

    private IActuator actuator; 
    private Socket sock; 

    public ActuatorThread(IActuator a, Socket s){
        actuator = a; 
        sock = s; 
    }

    public void run(){

        try {
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream()); 

            String readingAttrs = in.readUTF(); 
            boolean check = actuator.execute(readingAttrs);

            out.writeBoolean(check);
            sock.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
     
        

    }


}
