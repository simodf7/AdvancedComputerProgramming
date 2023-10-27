package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import interfaces.IDispatcher;
import interfaces.IPrinter;

public class DispatcherImpl extends UnicastRemoteObject implements IDispatcher {

    private Vector<IPrinter> printers;

    public DispatcherImpl() throws RemoteException{
        printers = new Vector<IPrinter>();
    }





    @Override
    public void addPrinter(int port) throws RemoteException{
        // TODO Auto-generated method stub
        IPrinter printer = new PrinterProxy("127.0.0.1", port);
        printers.add(printer);
        System.out.println("[DISPATCHER] Aggiunta printer");
    }

    @Override
    public boolean printRequest(String docName) throws RemoteException {
        // TODO Auto-generated method stub
        
        boolean result = false; 

        for(int i=0; i<printers.size(); i++){
            result = printers.get(i).print(docName); 
            if(result == true){
                System.out.println("[DISPATCHER] Printer trovata");
                return result; 
            }
        }

        System.out.println("[DISPATCHER] Printer non trovata");

        return result;


    }
    
}
