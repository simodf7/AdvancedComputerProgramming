package dispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import interfaces.IPrinter;

public class PrinterProxy implements IPrinter{

    private String address; 
    private int port; 

    public PrinterProxy (String a, int p){
        address = a; 
        port = p;
    }




    @Override
    public boolean print(String docName) {
        // TODO Auto-generated method stub

        boolean p = false; 

        try {
            Socket s = new Socket(address, port);

            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            DataInputStream dataIn = new DataInputStream(s.getInputStream()); 

            dataOut.writeUTF("print");
            dataOut.writeUTF(docName);

            p = dataIn.readBoolean();
            s.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return p;

    }




    
}
