package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import magazzino.IMagazzino;

public class MagazzinoSkeleton implements IMagazzino{

    private IMagazzino magazzino;
    private int port;

    public MagazzinoSkeleton(IMagazzino m, int p){
        magazzino = m;
        port = p;
    }

    public void runSkeleton(){

        try{
            ServerSocket server = new ServerSocket(port);

            System.out.println("[SERVER IN AZIONE]"); 
            while (true){
                Socket s = server.accept();

                ServerThread thread = new ServerThread(s, this);
                // crea un thread per ogni richiesta
            }

        }catch(IOException e){
            e.printStackTrace();
        }


    }

    public void deposita(String articolo, int id){
        magazzino.deposita(articolo, id);
    }

    public int preleva(String articolo){
        return magazzino.preleva(articolo);
    }
    
}
