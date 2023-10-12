package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IDispatcher;
import interfaces.IPrinter;

public class PrinterServer {
    
    public static void main(String[] args){

        

        try {
            Registry registry = LocateRegistry.getRegistry();
            IDispatcher stub = (IDispatcher) registry.lookup("dispatcher");
            stub.addPrinter(Integer.valueOf(args[0]));

            IPrinter printer = new PrinterImpl(args[1]);
            PrinterSkeleton skeleton = new PrinterSkeleton(Integer.valueOf(args[0]), printer);
            skeleton.runSkeleton();

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        

        
        
    }

    

}
