package broker;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class BrokerImpl extends UnicastRemoteObject implements IBroker {

    private Vector<AgenziaProxy> agenzie; 
    private int requests = 0;
    private int index = 0; 

    protected BrokerImpl() throws RemoteException {
        super();
        agenzie = new Vector<AgenziaProxy>();
    }

    @Override
    public void sottoscrivi(int porta) throws RemoteException {
        AgenziaProxy agenzia = new AgenziaProxy(porta);
        agenzie.add(agenzia);
        System.out.println("[BROKER] Subscription happened");
    }

    @Override
    public void sottoponi(int tipoOperazione, int quantita) throws RemoteException {
        if(tipoOperazione == 1){
            agenzie.get(index).acquista(quantita); 
            System.out.println("[BROKER] Acquisto completato da Agenzia: " + index );
        }else{
            agenzie.get(index).vendi(quantita);
            System.out.println("[BROKER] Vendita completata da Agenzia: " + index ) ;
        }

        requests++; 
        if(requests == 3){
            requests = 0; 
            index = (index + 1) % agenzie.size();
        }


    }

    
}
