package actuator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActuatorImpl extends ActuatorSkeleton{

    private Lock lock; 
    private File file; 

    public ActuatorImpl(int p) {
        super(p);
        lock = new ReentrantLock();
        file = new File("executions.txt");
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean execute(String readingAttrs) {
        
        Random random = new Random(); 

        try {
            Thread.sleep((random.nextInt(5) + 1) * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(lock.tryLock()) {

            System.out.println("[ACTUATOR] Received: " + readingAttrs);
        
            try {
                FileWriter writer = new FileWriter(file, true);
                writer.append(readingAttrs + "\n");
                writer.close();
                return true;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                lock.unlock();
            }

        }


        return false;




    }
        
}
