package generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import manager.IManager;

public class GeneratorThread extends Thread{

    public void run(){

        
        try {
            INotification alertNotification = new Notification(); 
            System.out.println("[GENERATOR] id: " + alertNotification.getId() + " criticality: " + alertNotification.getCriticality());
            Registry registry = LocateRegistry.getRegistry();
            IManager manager = (IManager) registry.lookup("manager");
            manager.sendNotification(alertNotification);


        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        


    }

}
