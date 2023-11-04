package sensorsManager;

import java.util.Random;

public class Reading implements IReading {

    private String type; 
    private int data; 
    protected static final long VersionNumber = 10; 
    private static final String[] types = {"temperature", "pressure"};

    public Reading(){
        Random random = new Random(); 
        type = types[random.nextInt(1)]; 
        data = random.nextInt(50) + 1; 
    }

    @Override
    public String getType() {
        return type; 
    }

    @Override
    public int getValue() {
        return data;
    }

}
