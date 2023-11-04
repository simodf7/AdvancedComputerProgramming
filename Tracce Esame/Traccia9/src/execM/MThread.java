package execM;

import java.rmi.RemoteException;

import server.IStorage;

public class MThread extends Thread{

    private IStorage storage; 
    private int result; 

    
    public MThread(IStorage s, int r){
        storage = s; 
        result = r; 
    }



    public void run(){

        try {
            storage.store("math", result);
            System.out.println("[EXEC M THREAD] Sent result: " + result);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
