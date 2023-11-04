package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Storage {
    
    public static void main(String[] args) {
        try {
            
            IStorage storage = new StorageImpl();
            Registry registry = LocateRegistry.getRegistry(); 
            registry.rebind("storage", storage);
            

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }
}
