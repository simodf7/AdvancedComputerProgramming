package client;

import java.rmi.RemoteException;
import java.util.Random;

import broker.IBroker;

public class ClientThread extends Thread{

    private IBroker broker; 
    private final static int R = 25; 

    public ClientThread(IBroker b){
        broker = b; 
    }

    public void run(){
        
        Random random = new Random(); 


        for(int i=0; i<R; i++){

            try {
                Thread.sleep(1000*(random.nextInt(3) +1));

                int op = random.nextInt(2) + 1; 
                int quantita = random.nextInt(5) + 1;
                broker.sottoponi(op, quantita);
                System.out.println("[CLIENT THREAD] Sottoposta richiesta di tipo: " + op + " quantita: " + quantita );

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            
            
        }
        
    }
}
