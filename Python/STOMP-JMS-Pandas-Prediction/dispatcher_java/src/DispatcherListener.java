import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

public class DispatcherListener implements MessageListener{

    private QueueSession qSession; 
    private Queue response; 
    private int port; 



    public DispatcherListener(QueueSession qSession, Queue response, int port) {
        this.qSession = qSession; 
        this.response = response; 
        this.port = port; 
    }

    @Override
    public void onMessage(Message message) {

        TextMessage msg = (TextMessage) message; 
        try {

            String text = msg.getText();
            System.out.println("[DISPATCHER] Arrived message: " + text);
            DispatcherProxy dispatcher = new DispatcherProxy(qSession, response, port); 
            
            int valore = Integer.parseInt(text.split("-")[1]); 
            System.out.println("[DISPATCHER] Sending forecast request for year:" + valore );
            dispatcher.forecast(valore);
            System.out.println("[DISPATCHER] Sent forecast request for year:" + valore );


        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 


    }

}
