package actuator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import controller.IController;

public class Actuator {
    
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]); 
        ActuatorImpl actuator = new ActuatorImpl(port);
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            IController controller = (IController) registry.lookup("controller");
            controller.addActuator(port);          
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        
        actuator.run_Skeleton();

    }



}
