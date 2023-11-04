package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StorageImpl extends UnicastRemoteObject implements IStorage{

    private File file; 
    private Lock lock; 

    protected StorageImpl() throws RemoteException {
        super();
        file = new File("results.txt"); 
        lock = new ReentrantLock(); 

    }

    @Override
    public void store(String type, int result) throws RemoteException {
        
        lock.lock();

        String text = type + ": "  + result; 
        System.out.println("[STORAGE] " + text);

        try {
            
            FileWriter writer = new FileWriter(file, true);
            writer.append(text + "\n"); 
            writer.close(); 


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

    }

    
}
