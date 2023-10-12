package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDispatcher extends Remote{

    void addPrinter(int port) throws RemoteException; 
    boolean printRequest(String docName) throws RemoteException; 

}