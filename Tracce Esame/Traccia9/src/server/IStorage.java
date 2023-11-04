package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IStorage extends Remote{

    void store(String type, int result) throws RemoteException; 
}
