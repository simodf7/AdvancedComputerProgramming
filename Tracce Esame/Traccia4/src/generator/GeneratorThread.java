package generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import manager.IManager;

public class GeneratorThread extends Thread{
    
    public void run(){
        try {

            Registry registry = LocateRegistry.getRegistry();
            IManager manager = (IManager) registry.lookup("manager");
            IOrder order = new Order();
            int result = manager.setOrder(order);
            System.out.println("[GENERATOR] Result: " + result);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
