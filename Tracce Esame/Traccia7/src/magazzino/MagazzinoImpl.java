package magazzino;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import client.IClient;

public class MagazzinoImpl extends UnicastRemoteObject implements IMagazzino{

    private int index = 0;
    private Vector<IClient> clients; 
    private int[] coda; 
    private int head; 
    private int tail; 
    private int elem; 
    private int size; 
    private Lock lock; 
    private Condition spazio_disp_cv; 
    private Condition risorse_disp_cv; 

    public MagazzinoImpl() throws RemoteException{
        clients = new Vector<IClient>(); 
        coda = new int[10]; 
        head = tail = 0; 
        elem = 0; 
        size = 10;
        lock = new ReentrantLock();
        spazio_disp_cv = lock.newCondition(); 
        risorse_disp_cv = lock.newCondition();

    }


    public boolean empty_queue(){
        return elem == 0; 
    }

    public boolean full_queue(){
        return elem == size; 
    }

    @Override
    public void sottoscrivi(IClient client) throws RemoteException {
        clients.add(client);
        System.out.println("[MAGAZZINO] Sottoscrizione avvenuta");
    }

    @Override
    public void deposita(int id) throws RemoteException {

        try{
            lock.lock();

            while (full_queue()) {
                spazio_disp_cv.await();
            }

            coda[head] = id; 
            head = (head +1) % size;
            elem++; 
            
            risorse_disp_cv.signal();

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            lock.unlock();
        }
        
        System.out.println("index: " + index);
        clients.get(index).nuovoArticolo();
        index = (index + 1) % clients.size();
        System.out.println("[MAGAZZINO] Notifcato client [" + index + "]");

    }

    @Override
    public int preleva() throws RemoteException {

        int id = 0;

        try{
            lock.lock();

            while (empty_queue()) {
                risorse_disp_cv.await();
            }

            id = coda[tail]; 
            tail = (tail +1) % size;
            elem--; 
            
            spazio_disp_cv.signal();

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            lock.unlock();
        }

        return id; 
        
    }

    
}
