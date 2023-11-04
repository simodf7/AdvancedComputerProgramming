package broker;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Broker {

    public static void main(String[] args) {
        try {
            IBroker broker = new BrokerImpl();
            Registry registry = LocateRegistry.getRegistry(); 
            registry.rebind("broker", broker);
            System.out.println("[BROKER] Oggetto Remoto Registrato" );


        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
    }
}
