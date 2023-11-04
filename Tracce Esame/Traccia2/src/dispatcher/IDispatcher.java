package dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

import generator.IReading;
import observer.IObserver;

public interface IDispatcher extends Remote{
    void setReading(IReading r) throws RemoteException; 
    void attach(String interest, IObserver o) throws RemoteException; 
    IReading getReading() throws RemoteException; 
}
