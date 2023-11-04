package rider;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import manager.IManager;

public class Rider {
    
    public static void main(String[] args) throws RemoteException {
        int location = Integer.valueOf(args[0]); 
        int port = Integer.valueOf(args[1]);

        RiderImpl impl = new RiderImpl(args[2]); 
        RiderSkeleton skeleton = new RiderSkeleton(impl, port);
        Registry registry = LocateRegistry.getRegistry(); 
        
        IManager manager;

        try {
            
            manager = (IManager) registry.lookup("manager");
            manager.subscribe(location, port);

        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        skeleton.runSkeleton();
    }
}
