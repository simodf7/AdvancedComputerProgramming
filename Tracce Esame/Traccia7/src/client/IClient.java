package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    void nuovoArticolo() throws RemoteException; 
}
