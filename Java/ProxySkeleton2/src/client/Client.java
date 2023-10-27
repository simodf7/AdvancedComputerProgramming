package client;

import magazzino.*;

public class Client{

    public static void main(String[] args) {

       IMagazzino magazzino = new MagazzinoProxy(args[0], Integer.valueOf(args[1]));

       for(int i=0; i<10; i++){
            ClientWorkerThread client = new ClientWorkerThread(i, magazzino);
            client.start();
       }



    }




}