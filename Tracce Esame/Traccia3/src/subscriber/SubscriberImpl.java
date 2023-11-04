package subscriber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SubscriberImpl implements ISubscriber{

    private File file; 

    public SubscriberImpl(String filename){
        file = new File(filename); 

    }


    @Override
    public void notifyAlert(int criticality) {

        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append(String.valueOf(criticality));
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        

    }
    
}
