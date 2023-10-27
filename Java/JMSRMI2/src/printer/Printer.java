package printer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.IPrinter;

public class Printer {
    
    public static void main(String[] args){

        try {

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IPrinter printer = new PrinterImpl(); 
            rmiRegistry.rebind("printer", printer);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
         




    }


}
