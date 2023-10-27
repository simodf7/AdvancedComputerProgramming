package client;

import interfaces.IDispatcher;

import java.rmi.RemoteException;
import java.util.Random;

public class ClientThreadWorker extends Thread {
 
    private int requests; 
    private IDispatcher dispatcher; 

    public ClientThreadWorker(int R, IDispatcher d){
        requests = R; 
        dispatcher = d;
    }

    public void run(){

        System.out.println("[THREAD CLIENT] CREATED");

        Random rand = new Random();
        int num = 0; 
        String docName;
        
        for(int i=0; i<requests; i++){
            num = rand.nextInt(50) + 1; 
            docName = "doc"+ num; 
            
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        
           boolean print = false;

            try {
                print = dispatcher.printRequest(docName);
                System.out.println("[THREAD CLIENT] REQUEST SENT");
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

           if(print){
                System.out.println("[THREAD CLIENT] PRINT REQUEST ACCEPTED");
           }
           else{
                System.out.println("[THREAD CLIENT] PRINT REQUEST NOT ACCEPTED");
           }
        }

    }


}
