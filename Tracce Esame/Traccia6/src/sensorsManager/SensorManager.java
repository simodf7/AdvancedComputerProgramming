package sensorsManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import controller.IController;

public class SensorManager {

    public static void main(String[] args) {
        
        Thread[] threads = new SensorThread[10]; 

        try {
            Registry rmiregistry = LocateRegistry.getRegistry();
            IController controller = (IController) rmiregistry.lookup("controller"); 

            for(int i=0; i<10; i++){
                SensorThread worker = new SensorThread(controller);
                worker.start();
                threads[i] = worker;
            }

            for(int i=0; i<10; i++){
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
