package generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dispatcher.IDispatcher;

public class Generator {

    public static void main(String[] args) {
        
        try {


            Registry rmiregistry = LocateRegistry.getRegistry();
            IDispatcher d = (IDispatcher) rmiregistry.lookup("dispatcher");

            for(int i=0; i<3; i++){
                GeneratorThread t = new GeneratorThread(); 
                t.start(); 
            }

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        



    }
    
}