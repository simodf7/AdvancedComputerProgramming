
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;

public class ExtractorListener implements MessageListener{

    private TopicPublisher temp_pub; 
    private TopicPublisher press_pub; 
    private TextMessage message_topic; 

    public ExtractorListener(TopicPublisher temp_pub, TopicPublisher press_pub, TextMessage mt) {
        this.temp_pub = temp_pub; 
        this.press_pub = press_pub; 
        this.message_topic = mt; 
    }

    @Override
    public void onMessage(Message message) {
        MapMessage msg = (MapMessage) message; 

        try {

            String type = msg.getString("type"); 
            int value = msg.getInt("value");
            System.out.println("[EXTRACTOR] Arrived message with type: "+ type + " value: "+ value );

            String value_text = String.valueOf(value);

            message_topic.setText(value_text);
            if(type.compareTo("temperature") == 0){
                System.out.println("[EXTRACTOR] Sent message to python with value: " + value_text);
                temp_pub.send(message_topic);
            }
            else{
                press_pub.send(message_topic);
            }



        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
