package manager;

import java.rmi.Remote;
import java.rmi.RemoteException;

import generator.INotification;

public interface IManager extends Remote {
    void sendNotification(INotification AlertNotification) throws RemoteException; 
    void subscribe(int componentId, int port) throws RemoteException; 
}
