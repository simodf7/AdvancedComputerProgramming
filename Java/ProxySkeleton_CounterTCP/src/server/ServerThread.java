package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import interfaces.ICounter;

public class ServerThread extends Thread {

    private ICounter CounterImpl; 
    private Socket client; 
    

    public ServerThread(ICounter c, Socket s){
        CounterImpl = c; 
        client = s; 
    }


    public void run(){

       
        try {

            DataOutputStream out = new DataOutputStream(client.getOutputStream()); 
            DataInputStream in = new DataInputStream(client.getInputStream());

            String method = in.readUTF(); 

            if(method.compareTo("setCount") == 0 ){
                String id = in.readUTF(); 
                int s = in.readInt(); 

                CounterImpl.setCount(id, s);
                System.out.println("[THREAD] Set count executed");

                out.writeUTF("ACK");


            }
            else if(method.compareTo("sum") == 0 ){

                int res = 0; 
            
                int s = in.readInt(); 

                res = CounterImpl.sum(s); 
              
                System.out.println("[THREAD] Sum executed");

                out.writeInt(res);
            }
            else if(method.compareTo("increment") == 0 ){


                int res = 0; 

                res = CounterImpl.increment(); 
              
                System.out.println("[THREAD] Increment executed");

                out.writeInt(res);


            }
            else{
                System.out.println("[THREAD] Method Unrecognised");
            }


            client.close();



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        




    }
    
}
