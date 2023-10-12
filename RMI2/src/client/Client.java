package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import interfaces.IDispatcher;

public class Client {

    public static void main(String[] args){

        System.out.println("[CLIENT PROCESS CREATED]");
        int T = 5; 
        int R = 3; 

        try{
            Registry registry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = (IDispatcher) registry.lookup("dispatcher");

            ClientThreadWorker[] threads = new ClientThreadWorker[T];
            
            for(int i=0; i<T; i++){
                threads[i] = new ClientThreadWorker(R, dispatcher);
                threads[i].start();
            }

            for(int i=0; i<T; i++){
                threads[i].join();
            }

        }
        catch(RemoteException e){
            e.printStackTrace();
        } 
        catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}