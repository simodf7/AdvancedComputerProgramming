package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import java.rmi.RemoteException;

import interfaces.IPrinter;

public class PrinterSkeleton implements IPrinter{
    
    private IPrinter printer;
    private int port; 

    public PrinterSkeleton(int pr, IPrinter p){
        port = pr; 
        printer = p;
    }

    public void runSkeleton(){

        try {
            ServerSocket socket = new ServerSocket(port);

            System.out.println("[SERVER IN AZIONE]"); 

            while (true) {
                Socket s = socket.accept();

                PrinterWorker thread = new PrinterWorker(s,this);
                thread.start();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public boolean print(String docName) throws RemoteException {
        // TODO Auto-generated method stub
        return printer.print(docName);
    }



}
