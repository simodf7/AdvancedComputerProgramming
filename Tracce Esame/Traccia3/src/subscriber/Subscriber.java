package subscriber;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import manager.IManager;

public class Subscriber {

    public static void main(String[] args) {

        int port = Integer.parseInt(args[1]);
        int componentId = Integer.parseInt(args[0]);

        Registry registry;
        try {

            registry = LocateRegistry.getRegistry();
            IManager manager = (IManager) registry.lookup("manager"); 
            manager.subscribe(componentId, port);
            System.out.println("[SUBSCRIBER " + componentId + " ] Subscribed");


        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        SubscriberImpl impl = new SubscriberImpl(args[2]);
        SubscriberSkeleton skeleton = new SubscriberSkeleton(port, impl);
        skeleton.runSkeleton();

    }


}
