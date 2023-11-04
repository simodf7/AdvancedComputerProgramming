package magazzino;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.IClient;

public interface IMagazzino extends Remote{
    void sottoscrivi(IClient client) throws RemoteException; 
    void deposita(int id) throws RemoteException; 
    int preleva() throws RemoteException; 
}
