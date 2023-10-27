package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.IServer;

public class Server {


    public static void main(String[] args){

        try {
            Registry rmiregistry = LocateRegistry.getRegistry();
            IServer server = new ServerImpl(5);     
            rmiregistry.rebind("server", server);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        



    }
    
}
