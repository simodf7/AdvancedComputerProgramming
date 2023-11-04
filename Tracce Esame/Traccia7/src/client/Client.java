package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import magazzino.IMagazzino;

public class Client {

    public static void main(String[] args) {

        Registry registry;
        try {
            registry = LocateRegistry.getRegistry();
            IMagazzino magazzino = (IMagazzino) registry.lookup("magazzino"); 
            IClient client = new ClientImpl(magazzino); 
            magazzino.sottoscrivi(client);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
    }
}