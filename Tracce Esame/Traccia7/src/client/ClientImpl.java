package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import magazzino.IMagazzino;

public class ClientImpl extends UnicastRemoteObject implements IClient{

    private IMagazzino magazzino; 

    public ClientImpl(IMagazzino m) throws RemoteException{
        this.magazzino = m; 
    }


    @Override
    public void nuovoArticolo() {

        try {
            System.out.println("[CLIENT] Avvenuta Notifica");
            int id = magazzino.preleva();
            System.out.println("[CLIENT] Prelevato id: " + id);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }
        
}
