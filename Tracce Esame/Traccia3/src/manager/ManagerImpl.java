package manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import generator.INotification;

public class ManagerImpl extends UnicastRemoteObject implements IManager {

    private Vector<SubscriberProxy> subs; 

    public ManagerImpl() throws RemoteException{
        subs = new Vector<SubscriberProxy>(); 
    }

    @Override
    public synchronized void sendNotification(INotification AlertNotification) throws RemoteException {
        for(int i=0; i<subs.size(); i++){
            if(subs.get(i).getId() == AlertNotification.getId()){
                subs.get(i).notifyAlert(AlertNotification.getCriticality());
            }
        }
    }

    @Override
    public void subscribe(int componentId, int port) throws RemoteException {

        SubscriberProxy sub = new SubscriberProxy(componentId, port); 
        subs.add(sub);
        System.out.println("[MANAGER] Subscription happened");
    }
    
}
