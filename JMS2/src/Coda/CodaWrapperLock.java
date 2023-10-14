package Coda;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CodaWrapperLock extends CodaWrapper {

    private Lock lock;
    private Condition SpazioDisponibile; 
    private Condition ComandoDisponibile;
    


    public CodaWrapperLock(ICoda c) {
        super(c);
        lock = new ReentrantLock();
        SpazioDisponibile = lock.newCondition();
        ComandoDisponibile = lock.newCondition();
    }

    @Override
    public void inserisci(String cmd) {
        // TODO Auto-generated method stub

        try{
            lock.lock();
            while(coda.full()){
                try {
                    SpazioDisponibile.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            coda.inserisci(cmd);
            ComandoDisponibile.signal();
        }
        finally{
            lock.unlock();
        }


    }

    @Override
    public String preleva() {
        // TODO Auto-generated method stub
        String cmd = null;

        try{
            lock.lock();
            while(coda.empty()){
                try {
                    ComandoDisponibile.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            cmd = coda.preleva();
            SpazioDisponibile.signal();
        }
        finally{
            lock.unlock();
        }

        return cmd;
    }
    
    
}
