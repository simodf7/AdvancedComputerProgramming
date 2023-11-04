package controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller {
    
    public static void main(String[] args) {

        try {
            
            Registry registry = LocateRegistry.getRegistry();
            IController controller = new ControllerImpl(); 
            registry.rebind("controller", controller);


        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        

    }

}
