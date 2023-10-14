package Sensor;

import Coda.ICoda;

public class SensorManager extends Thread{

    private ICoda coda; 
    private String command;

    public SensorManager(ICoda c, String cmd){
        coda = c;
        command = cmd;

    }


    public void run(){
        coda.inserisci(command);
        System.out.println("[SUBSCRIBER-MANAGER] Message inserted on queue");
    }
    
}
