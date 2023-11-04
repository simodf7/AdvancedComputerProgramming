package execM;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import server.IStorage;

public class MListener implements MessageListener{

    private IStorage storage; 

    public MListener(IStorage s){
        storage = s;
    }

    @Override
    public void onMessage(Message message) {
        MapMessage msg = (MapMessage) message; 

        try {
            
            int param1 = msg.getInt("param1");
            int param2 = msg.getInt("param2");  


            int result = param1 * param2; 

            MThread t = new MThread(storage, result); 
            t.start(); 


        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
              

    }
    
}
