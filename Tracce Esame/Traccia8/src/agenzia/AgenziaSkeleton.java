package agenzia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AgenziaSkeleton implements IAgenzia{

    private int port; 

    public AgenziaSkeleton(int p){
        port = p;
    }

    public void run_Skeleton(){

        try {
            
            ServerSocket socket = new ServerSocket(port);

            while(true){
                
                Socket client = socket.accept(); 

                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream()); 

                String method = in.readUTF();
                int quantita = in.readInt();

                if(method.compareTo("acquista") == 0){
                    acquista(quantita);
                    System.out.println("[SERVER] Acquisto completato");
                }
                else if(method.compareTo("vendi") == 0){
                    vendi(quantita);
                    System.out.println("[SERVER] Vendita completata");
                }else{
                    System.out.println("Method not recognised");
                }


            }
               


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        
    }
    
}
