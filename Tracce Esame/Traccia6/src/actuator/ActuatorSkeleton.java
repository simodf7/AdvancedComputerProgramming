package actuator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class ActuatorSkeleton implements IActuator{

    private int port; 

    public ActuatorSkeleton(int p){
        this.port = p;
    }

    public void run_Skeleton(){

        try {
            ServerSocket sock = new ServerSocket(port);

            while (true) {
                Socket client = sock.accept();

                ActuatorThread t = new ActuatorThread(this, client);
                t.start();

            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        

    }
    
}
