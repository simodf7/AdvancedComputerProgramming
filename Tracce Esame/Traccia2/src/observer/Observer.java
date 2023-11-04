package observer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dispatcher.IDispatcher;

public class Observer {
    
    public static void main(String[] args) {
        
        try {
            System.err.println(args[1]);
            ObserverImpl observer = new ObserverImpl(args[1]);
            Registry registry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = (IDispatcher) registry.lookup("dispatcher"); 
            System.out.println(args[0]);
            dispatcher.attach(args[0], observer);



        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        

    }


}
