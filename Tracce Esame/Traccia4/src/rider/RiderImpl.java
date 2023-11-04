package rider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RiderImpl implements IRider {

    private File file;

    public RiderImpl(String filename){
        file = new File(filename);
    }

    @Override
    public void notifyOrder(int id, String address) {
        System.out.println("[RIDER] Ricevuti: " + id + " " + address);

        FileWriter writer;
        try {
            
            writer = new FileWriter(file, true);
            writer.append(id + " " + address + "\n");
            writer.close();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
}
