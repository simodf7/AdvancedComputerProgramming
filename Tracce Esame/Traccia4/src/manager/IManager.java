package manager;

import java.rmi.Remote;
import java.rmi.RemoteException;

import generator.IOrder;

public interface IManager extends Remote {
    int setOrder(IOrder order) throws RemoteException;
    void subscribe(int location, int port) throws RemoteException; 
}
