package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

import interfaces.ILogger;

public abstract class ServerSkeleton implements ILogger {

    private int port; 


    public ServerSkeleton(int p) {
        port = p;
    }

    public void runSkeleton(){

       

        try {
            

            DatagramSocket socket = new DatagramSocket(port);

            while (true) {
                byte[] buffer = new byte[1048]; 
                DatagramPacket request = new DatagramPacket(buffer, buffer.length); 

                socket.receive(request);

                ServerThread thread = new ServerThread(request, this); 
                thread.start(); 
            }




        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
            



        }

        




}


