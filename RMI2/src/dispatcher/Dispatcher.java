package dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IDispatcher;
import interfaces.IPrinter;

public class Dispatcher {
    

    public static void main(String[] args){

        Registry registry; 
        
        try {
            registry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = new DispatcherImpl();
            registry.rebind("dispatcher", dispatcher);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        

    }
}
