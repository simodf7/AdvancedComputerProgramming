package broker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import agenzia.IAgenzia;

public class AgenziaProxy implements IAgenzia{

    private int porta;

    public AgenziaProxy(int porta) {
        this.porta = porta; 
    }

    @Override
    public void acquista(int quantita) {
        // TODO Auto-generated method stub
        
        try {
            Socket socket = new Socket("127.0.0.1", porta);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            DataInputStream in = new DataInputStream(socket.getInputStream()); 


            out.writeUTF("acquista");
            out.writeInt(quantita);

            socket.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         

    }

    @Override
    public void vendi(int quantita) {
        // TODO Auto-generated method stub

        try {
            Socket socket = new Socket("127.0.0.1", porta);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            DataInputStream in = new DataInputStream(socket.getInputStream()); 


            out.writeUTF("vendi");
            out.writeInt(quantita);

            socket.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
    }

}
