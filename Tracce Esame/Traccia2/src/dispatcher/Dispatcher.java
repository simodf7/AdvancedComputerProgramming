package dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Dispatcher {
    
    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = new DispatcherImpl();
            registry.rebind("dispatcher", dispatcher);


        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
}
