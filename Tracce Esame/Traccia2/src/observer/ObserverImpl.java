package observer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import dispatcher.IDispatcher;
import generator.IReading;

public class ObserverImpl extends UnicastRemoteObject implements IObserver {

    public IDispatcher dispatcher; 
    private File file; 

    public ObserverImpl(String filename) throws RemoteException, NotBoundException{
        Registry registry = LocateRegistry.getRegistry(); 
        dispatcher = (IDispatcher) registry.lookup("dispatcher");

        file = new File(filename); 

    }

    @Override
    public void notifyReading() {
       

        try {
            
            IReading reading = dispatcher.getReading();
            FileWriter writer = new FileWriter(file, true);
            writer.append(reading.getValore() + "\n"); 
            writer.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        


    }
    
}
