package disk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import interfaces.ILogger;

public class LoggerProxy implements ILogger{

    private int porta;

    public LoggerProxy(int p){
        porta = p; 
    }

    @Override
    public void registraDato(int dato) {

        try {

            DatagramSocket socket = new DatagramSocket();

            String message = new String("registraDato#" + dato);

            DatagramPacket request = new DatagramPacket(message.getBytes(), message.getBytes().length, 
            InetAddress.getLocalHost(), porta ); 
            

            socket.send(request);


            socket.close();



        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        



    }


    
}
