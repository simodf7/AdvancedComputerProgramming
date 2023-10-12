package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import interfaces.IPrinter;

public class PrinterImpl implements IPrinter{

    private ReentrantLock lock; 
    private String file;
    private static int id = 0; 

    public PrinterImpl(String s){
        file = s;
        lock = new ReentrantLock();
        id++;
    }


    @Override
    public boolean print(String docName) {
        // TODO Auto-generated method stub

        if(lock.tryLock()){
            try{
                System.out.println("[PRINTER " + id + "] print: " + docName);
                try {
                      BufferedWriter out = new BufferedWriter(
                    new FileWriter(file, true));
        
                    // Writing on output stream
                    out.write(docName);
                    // Closing the connection
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            finally{
                lock.unlock();
            }

            return true;

        }

        System.out.println("[PRINTER "+ id + "] No printer Available");
        return false;
    }
    
}
