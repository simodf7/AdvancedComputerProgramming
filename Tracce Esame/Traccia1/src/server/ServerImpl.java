package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import interfaces.ILogger;

public class ServerImpl extends ServerSkeleton {

    private Lock lock;
    private File file;  
 
    public ServerImpl(int p){
        super(p);
        lock = new ReentrantLock(); 
        file = new File("saves.txt");

    }



   
   

    @Override
    public void registraDato(int dato) {
        
        lock.lock(); 

        FileWriter writer; 

        try{
            System.out.println("[SERVER] Saved: " + dato);
            writer = new FileWriter(file, true);
            writer.append("Saved: " + dato); 

            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            lock.unlock();
            
        }

        

        
        



    }
    


    
}
