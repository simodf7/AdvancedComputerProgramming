package Sensor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.crypto.interfaces.PBEKey;

import Coda.ICoda;

public class SensorExecutor extends Thread{

    private ICoda coda; 
    private File file; 

    public SensorExecutor(ICoda c){
        coda = c;
        file = new File("cmdlog.txt");
    }
    
    public void run(){
        
        boolean emptied = false; 

        while(true){

            try{

                Thread.sleep(10000);
               
                FileWriter writer = new FileWriter(file, true);
                
                while(!coda.empty()){ 
                    emptied = true;
                    String cmd = coda.preleva();
                    writer.write(cmd + "\n");
                }

                if(emptied){
                    System.out.println("[SUBSCRIBER-EXECUTOR] Messages taken from queue");
                    emptied = false;
                }
                else{
                    System.out.println("[SUBSCRIBER-EXECUTOR] Queue already empty");
                }
                

                writer.close();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }

        
        
    }
}
