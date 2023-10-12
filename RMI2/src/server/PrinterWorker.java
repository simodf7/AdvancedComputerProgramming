package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import interfaces.IPrinter;

public class PrinterWorker extends Thread {
    
    private IPrinter printer;
    private Socket sock;

    public PrinterWorker(Socket s, IPrinter p){
        printer = p;
        sock = s;
    }

    public void run(){

        boolean result = false;

        try {
            DataInputStream dataIn = new DataInputStream(sock.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(sock.getOutputStream());

            String method = dataIn.readUTF(); 
            
            if(method.compareTo("print") == 0){

                System.out.println("[PRINTER] COMANDO PRINT RICEVUTO");
                String docName = dataIn.readUTF();
                
                result = printer.print(docName);
                
                dataOut.writeBoolean(result);
                dataIn.close();
                dataOut.close();
            }
            else{
                System.out.println("[PRINTER] Method not recognised");
            }

            sock.close();

        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        


    }

}
