import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueSender;
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
            
            DispatcherProxy dispatcher = new DispatcherProxy(port); 

            if(text.compareTo("preleva") == 0){
                int result = dispatcher.preleva(); 

                TextMessage txt = qSession.createTextMessage(); 
                txt.setText(String.valueOf(result));
                QueueSender sender = qSession.createSender(response); 
                sender.send(txt);

            }else{

                String[] tokens = text.split("-"); 
                int id = Integer.parseInt(tokens[1]); 

                dispatcher.deposita(id);

                TextMessage txt = qSession.createTextMessage(); 
                txt.setText("depositato");
                QueueSender sender = qSession.createSender(response); 
                sender.send(txt);


            }






        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        


    }
        
}
