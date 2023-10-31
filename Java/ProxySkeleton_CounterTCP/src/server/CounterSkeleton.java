package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import interfaces.ICounter;

public class CounterSkeleton implements ICounter {

    private ICounter CounterImpl; 
    private int port; 


    public CounterSkeleton(ICounter i, int p){
        CounterImpl = i; 
        this.port = p;
    }


    public void runSkeleton(){

        try {

            ServerSocket server = new ServerSocket(port);

            while(true){

                Socket client = server.accept(); 

                ServerThread worker = new ServerThread(this, client); 
                worker.start();
                


            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 




    }


    @Override
    public void setCount(String id, int s) {
        // TODO Auto-generated method stub
        CounterImpl.setCount(id, s);
    }

    @Override
    public int sum(int s) {
        return CounterImpl.sum(s);
    }

    @Override
    public int increment() {
        return CounterImpl.increment();
    }
    
}
