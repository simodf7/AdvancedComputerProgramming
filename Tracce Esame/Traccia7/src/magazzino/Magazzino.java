package magazzino;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Magazzino {

    public static void main(String[] args) {

        try {
            MagazzinoImpl magazzino = new MagazzinoImpl();
            Registry registry = LocateRegistry.getRegistry(); 
            registry.rebind("magazzino", magazzino);


        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }
}
