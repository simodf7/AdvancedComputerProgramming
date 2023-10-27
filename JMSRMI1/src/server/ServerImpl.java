package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import service.IServer;

public class ServerImpl extends UnicastRemoteObject implements IServer{

    private static final long serialVersionUID = 851495907233092267L;

    private int data[]; 
    private int size; 
    private int elem;
    private int head;
    private int tail; 

    private Lock lock; 
    private Condition spazio_disp; 
    private Condition risorse_disp; 
    

    public ServerImpl(int N) throws RemoteException{

        size = N; 
        data = new int[size]; 
        elem = 0; 
        head = tail = 0; 

        lock = new ReentrantLock(); 
        spazio_disp = lock.newCondition(); 
        risorse_disp = lock.newCondition(); 

        
    }



    public boolean empty(){
        return elem == 0; 

    }

    public boolean full(){
        return elem == size; 
    }

    public int getSize(){
        return size; 
    }





    @Override
    public void deposita(int id) {
        
        lock.lock();

        try{

            while(full()){
                try {
                    spazio_disp.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            data[head] = id; 
            head = (head+1) % size; 
            elem++; 

            risorse_disp.signal();
 

        }
        finally{
            lock.unlock();
        }



    }



    @Override
    public int preleva() {
          
        int id = 0; 

        lock.lock();

        try{

            while(empty()){
                try {
                    risorse_disp.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            id = data[tail];  
            tail = (tail + 1) % size;
            elem--; 

            spazio_disp.signal();
 

        }
        finally{
            lock.unlock();
        }

        return id;
    } 

    

    
}
