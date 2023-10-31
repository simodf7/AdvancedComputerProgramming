package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import interfaces.ICounter;

public abstract class CounterSkeleton implements ICounter{


    public void runSkeleton(){

        try {



            DatagramSocket socket = new DatagramSocket(9000);

            while(true){

                byte[] buffer = new byte[100]; 

                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                socket.receive(reply);
                
               CounterWorker worker = new CounterWorker(socket, reply, this);
               worker.start();


            }






        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }



}
