package execT;

import java.util.StringTokenizer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import server.IStorage;

public class TListener implements MessageListener{

    private IStorage storage; 

    public TListener(IStorage storage) {
        this.storage = storage; 
    }

    @Override
    public void onMessage(Message message) {
        TextMessage msg = (TextMessage) message; 

        try {
            
            String text = msg.getText(); 

            StringTokenizer tokens = new StringTokenizer(text, "#"); 
            

            String save = tokens.nextToken(); 
            int result = Integer.parseInt(tokens.nextToken());

            TThread t = new TThread(storage, result); 
            t.start();



        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
              

    }
}
