package agenzia;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AgenziaImpl extends AgenziaSkeleton{

    private Lock lock; 
    private Condition acquista_cv;
    private int num_biglietti; 


    public AgenziaImpl(int p) {
        super(p);
        num_biglietti = 10;
        lock = new ReentrantLock();
        acquista_cv = lock.newCondition();
    }

    public boolean ticket_available(int quantita){
        return num_biglietti >= quantita;
    }

    @Override
    public void acquista(int quantita) {

        Random random = new Random(); 
        try {
            Thread.sleep(1000*(random.nextInt(4) + 4));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try{
            lock.lock();

            while (!ticket_available(quantita)){
                try {
                    acquista_cv.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            num_biglietti -= quantita;
            System.out.println("[AGENZIA] Numero Biglietti dopo acquisto: " + num_biglietti );
        }
        finally{
            lock.unlock();
        }

        


    }

    @Override
    public void vendi(int quantita) {

        Random random = new Random(); 
        try {
            Thread.sleep(1000*(random.nextInt(4) + 4));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       

       lock.lock();

        
       num_biglietti += quantita;
       acquista_cv.signal();

       System.out.println("[AGENZIA] Numero Biglietti dopo vendita: " + num_biglietti );

       lock.unlock();

       
    }
    

    

}
