package produttore;

import java.rmi.RemoteException;
import java.util.Random;

import magazzino.IMagazzino;

public class ProduttoreThread extends Thread{
    

    private IMagazzino magazzino; 


    public ProduttoreThread(IMagazzino m){
        magazzino = m; 
    }

    public void run(){
        Random random = new Random(); 
        try {
            Thread.sleep((random.nextInt(5) + 1)*1000);
            int id = random.nextInt(100) + 1; 
            magazzino.deposita(id);
            System.out.println("[PRODUTTORE THREAD] Deposito: " + id);



        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         

    }


}
