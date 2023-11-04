package execT;

import java.rmi.RemoteException;

import server.IStorage;

public class TThread extends Thread {

    private IStorage storage; 
    private int result; 
    
    public TThread(IStorage storage, int r) {
        this.storage = storage; 
        result = r; 
    }

    public void run(){

        
        try {
            storage.store("text", result);
            System.out.println("[EXEC T THREAD] Sent result: " + result);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


}
