package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.StringTokenizer;

import interfaces.ILogger;

public class ServerThread extends Thread {
    
    private DatagramPacket request; 
    private ILogger logger; 

    public ServerThread(DatagramPacket r, ILogger l){
        request = r; 
        logger = l;
    }


    public void run(){

        String message = new String(request.getData(), 0, request.getLength());
        StringTokenizer messageTokens = new StringTokenizer(message, "#"); 

        String method = messageTokens.nextToken(); 

        if(method.compareTo("registraDato") == 0 ){
            int dato = Integer.valueOf(messageTokens.nextToken()).intValue(); 
            System.out.println("[SERVER THREAD] Requested: " + dato);
            logger.registraDato(dato);
        }
        else{
            System.out.println("[SERVER THREAD] Method unrecognised");
        }
        


    }

}
