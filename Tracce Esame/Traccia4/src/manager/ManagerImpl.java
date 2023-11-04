package manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import generator.IOrder;

public class ManagerImpl extends UnicastRemoteObject implements IManager{

    private Vector<RiderProxy> riders; 

    protected ManagerImpl() throws RemoteException {
        super();
        riders = new Vector<RiderProxy>();
        //TODO Auto-generated constructor stub
    }

    @Override
    public synchronized int setOrder(IOrder order) throws RemoteException {
        
        int location = order.getLocation(); 
        System.out.println("[MANAGER] ORDER WITH LOCATION: " + location );
        int id = order.getID();
        String address = order.getAddress();
        int result = -1; 

        for(int i=0; i<riders.size(); i++){
            if(riders.get(i).getLocation() == location){
                riders.get(i).notifyOrder(id, address);
                result = 1;
            }
        }

        return result; 
    }

    @Override
    public void subscribe(int location, int port) throws RemoteException {
        System.out.println("[MANAGER] Subscription happened on location " +  location);
        RiderProxy rider = new RiderProxy(location, port);
        riders.add(rider);

    }
    
}
