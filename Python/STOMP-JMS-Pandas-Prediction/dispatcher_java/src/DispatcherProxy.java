import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

public class DispatcherProxy implements IDispatcher{

    private QueueSession qSession; 
    private Queue response; 
    private int port; 

    public DispatcherProxy(QueueSession qSession, Queue response, int port) {
        this.qSession = qSession; 
        this.response = response;
        this.port = port; 
    }

    @Override
    public void forecast(int valore) {

        try {
            Socket socket = new Socket("127.0.0.1", port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.writeUTF("forecast-" +valore);

            String result = in.readLine(); 

            socket.close();
            
            TextMessage message = qSession.createTextMessage(); 
            message.setText(result);
            QueueSender sender = qSession.createSender(response);
            sender.send(message);

           


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        

    }

    
}
