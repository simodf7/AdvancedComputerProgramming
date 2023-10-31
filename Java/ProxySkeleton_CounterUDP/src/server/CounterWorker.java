package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

import interfaces.ICounter;

public class CounterWorker extends Thread {

    private DatagramSocket socket; 
    private DatagramPacket packet; 
    private ICounter skeleton; 

    public CounterWorker (DatagramSocket sock, DatagramPacket p, ICounter s){
        this.socket = sock; 
        this.packet = p; 
        this.skeleton = s; 
    }




    public void run(){

        String request_message = new String(packet.getData(), 0, packet.getLength());
        
        StringTokenizer messageTokens = new StringTokenizer(request_message, "#"); 

        String method = messageTokens.nextToken(); 

        if(method.compareTo("setCount") == 0 ){

            String id = messageTokens.nextToken(); 
            int s = Integer.valueOf(messageTokens.nextToken()).intValue();

            skeleton.setCount(id, s);

            String reply_message = new String("Ack"); 
            DatagramPacket reply = new DatagramPacket(reply_message.getBytes(), reply_message.getBytes().length,
            packet.getAddress(), packet.getPort());

            try {
                socket.send(reply);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        


        }
        else if(method.compareTo("sum") == 0 ){

        
            int s = Integer.valueOf(messageTokens.nextToken()).intValue();

            int res = skeleton.sum(s);



            String reply_message =  Integer.toString(res); 
            DatagramPacket reply = new DatagramPacket(reply_message.getBytes(), reply_message.getBytes().length,
            packet.getAddress(), packet.getPort());

            try {
                socket.send(reply);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }
        else if(method.compareTo("increment") == 0 ){


            int res = skeleton.increment();

            String reply_message = Integer.toString(res);
            DatagramPacket reply = new DatagramPacket(reply_message.getBytes(), reply_message.getBytes().length,
            packet.getAddress(), packet.getPort());

            try {
                socket.send(reply);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }




        }
        else{
            System.out.println("Method not recognised");
        }

        





    }

}
