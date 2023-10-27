package printer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import interfaces.IPrinter;

public class PrinterImpl extends UnicastRemoteObject implements IPrinter{

    private File myFile; 
    private Lock lock; 
    

    protected PrinterImpl() throws RemoteException {
        super();
        myFile = new File("doc.txt");
        lock = new ReentrantLock(); 
    }

    @Override
    public void printDoc(String docName) throws RemoteException {

        lock.lock();

        try{

            System.out.println("[PRINTER] Received request: " + docName);
            Thread.sleep(5000);
            FileWriter writer = new FileWriter(myFile, true);
            writer.write(docName +"\n");
            System.out.println("[PRINTER] Printer wrote on file");
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            lock.unlock();
        }

    }



    
}
