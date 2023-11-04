package produttore;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import magazzino.IMagazzino;

public class Produttore {
    
    public static void main(String[] args) {
        
        Thread[] threads = new ProduttoreThread[3];

        try {
            Registry registry = LocateRegistry.getRegistry();
            IMagazzino magazzino = (IMagazzino) registry.lookup("magazzino");
            
            for(int i=0; i<3; i++){
                ProduttoreThread t = new ProduttoreThread(magazzino); 
                t.start();
                threads[i] = t; 
            }

            for(int i=0; i<3; i++){
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
