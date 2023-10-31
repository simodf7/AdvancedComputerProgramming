package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import interfaces.ICounter;

public class CounterProxy implements ICounter {

    private DatagramSocket socket; 


    public CounterProxy(){
        
        try {
            socket = new DatagramSocket();

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void setCount(String id, int s) {
        
        String message = new String("setCount#" + id + "#" + s  + "#");

        try {


            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length,
            InetAddress.getLocalHost(), 9000);

            socket.send(packet);

            byte[] buffer = new byte[100]; 
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length); 
            
            socket.receive(reply);



        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 


    }

    @Override
    public int sum(int s) {

        int x = 0; 
        String message = new String("sum#" + s + "#");

        try {

            DatagramPacket request = new DatagramPacket(message.getBytes(), message.getBytes().length,
            InetAddress.getLocalHost(), 9000);

            socket.send(request);
            
            byte[] buffer = new byte[100]; 
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

            socket.receive(reply);

            String reply_message = new String(reply.getData(), 0, reply.getLength());

            x = Integer.valueOf(reply_message).intValue();


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        return x; 

    }

    @Override
    public int increment() {
        int x=0; 

        String message = new String("increment#"); 
        
        try {

            DatagramPacket request = new DatagramPacket(message.getBytes(), message.getBytes().length, 
            InetAddress.getLocalHost(), 9000);

            socket.send(request);
            
            byte[] buffer = new byte[100]; 
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

            socket.receive(reply);

            String reply_message = new String(reply.getData(), 0, reply.getLength());

            x = Integer.valueOf(reply_message).intValue();



        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        





        return x; 
    }
    
}
