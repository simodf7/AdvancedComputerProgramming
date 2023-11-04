package generator;

import java.util.Random;

public class Notification implements INotification{

    private int id; 
    private int criticality; 

    public Notification(){
        Random random = new Random(); 
        this.id = random.nextInt(5) + 1;
        this.criticality = random.nextInt(3) + 1;
    }


    @Override
    public void setId(int id) {
        this.id = id; 
    }

    @Override
    public void setCriticality(int critic) {
        this.criticality = critic; 
    }

    @Override
    public int getId() {
        return id; 
    }

    @Override
    public int getCriticality() {
        return criticality;
    }

    
}
