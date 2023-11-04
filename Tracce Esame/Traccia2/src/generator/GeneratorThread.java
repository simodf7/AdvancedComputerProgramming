package generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dispatcher.IDispatcher;

public class GeneratorThread extends Thread{

    private IDispatcher d; 

    public void run(){

        for(int i=0; i<3; i++){

            Reading r = new Reading();
            try {
                d.setReading(r);
                Thread.sleep(5000);
                
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            


        }

    }

    public GeneratorThread(){

        
        try {
            Registry registry = LocateRegistry.getRegistry();
            d = (IDispatcher) registry.lookup("dispatcher"); 

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        

    }
}
