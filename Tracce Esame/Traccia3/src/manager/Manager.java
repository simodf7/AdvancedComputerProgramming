package manager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Manager {
    
    public static void main(String[] args) {
        

        try {
            IManager manager = new ManagerImpl(); 
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("manager", manager);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
    }
}
