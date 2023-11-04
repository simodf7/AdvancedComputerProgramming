package agenzia;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import broker.IBroker;

public class Agenzia {
    
    public static void main(String[] args){
        int port = Integer.parseInt(args[0]);
       
        System.out.println("Port: " + port);

        try {

            Registry registry = LocateRegistry.getRegistry();
            IBroker broker = (IBroker) registry.lookup("broker");
            broker.sottoscrivi(port);
            
            AgenziaImpl agenzia = new AgenziaImpl(port);
            agenzia.run_Skeleton();
            

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 


       
    }
}
