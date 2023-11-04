package disk;

import interfaces.ILogger;

public class DiskThread extends Thread{

    private int dato; 
    private int porta; 

    public DiskThread(int dato, int porta) {
        this.dato = dato; 
        this.porta = porta; 
    }


    public void run(){


        ILogger proxy = new LoggerProxy(porta); 
        proxy.registraDato(dato);


    }
    
}
