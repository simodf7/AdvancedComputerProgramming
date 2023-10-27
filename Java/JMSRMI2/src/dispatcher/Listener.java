package dispatcher;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import interfaces.IPrinter;

public class Listener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        MapMessage msg = (MapMessage) message; 
        String document;
        try {

            document = msg.getString("nomeDocumento");
            String printerName = msg.getString("nomePrinter");
            System.out.println("[LISTENER] Message received: " + document );

            Registry rmiRegistry = LocateRegistry.getRegistry(); 
            IPrinter printer = (IPrinter) rmiRegistry.lookup(printerName);
            printer.printDoc(document);
            System.out.println("[LISTENER] Listener sent request to remote object");



        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        

    }
    
}
