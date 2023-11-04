package broker;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBroker extends Remote {
    void sottoscrivi(int porta) throws RemoteException;
    void sottoponi(int tipoOperazione, int quantita) throws RemoteException;
}
