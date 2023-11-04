package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Vector;

import generator.IReading;
import observer.IObserver;

public class DispatcherImpl extends UnicastRemoteObject implements IDispatcher{

    private IReading reading; 
    private Vector<IObserver> obsv_press;
    private Vector<IObserver> obsv_temp; 
    private static final long SerialVersionUID = 10; 


    public DispatcherImpl() throws RemoteException{

        obsv_press = new Vector<IObserver>(); 
        obsv_temp = new Vector<IObserver>();
    }

    @Override
    public synchronized void setReading(IReading r) throws RemoteException {
        reading = r; 

        Random random = new Random(); 
        try {

            Thread.sleep(1000* (random.nextInt(5) + 1) );

            if(r.getTipo().compareTo("temperatura") == 0){
                for(int i=0; i<obsv_temp.size(); i++){
                    obsv_temp.get(i).notifyReading();
                }
            }
            else{
                for(int i=0; i<obsv_press.size(); i++){
                    obsv_press.get(i).notifyReading();
                }
            }



        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        
    }

    @Override
    public void attach(String interest, IObserver o) throws RemoteException {

        if(interest.compareTo("temperatura") == 0){
            obsv_temp.add(o);   

        }

        else{
            obsv_press.add(o);
        }

    }

    @Override
    public IReading getReading() throws RemoteException {
        return reading; 
    }

}
