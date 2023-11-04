package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import broker.IBroker;

public class Client {
    
    private final static int T = 5; 

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry();
            IBroker broker = (IBroker) registry.lookup("broker");

            Thread[] threads = new ClientThread[T];

            for(int i=0; i<T; i++){
                ClientThread t = new ClientThread(broker); 
                t.start();
                threads[i] = t; 

            }
            
            for(int i=0; i<T; i++){
                threads[i].join(); 

            }
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
    }


}
