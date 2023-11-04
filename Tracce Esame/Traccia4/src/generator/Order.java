package generator;

import java.util.Random;

public class Order implements IOrder {
    
    private int id; 
    private int location; 
    private String address; 
    protected final static long VersionNumber = 10;
    
    public Order() {
        Random random = new Random(); 

        id = random.nextInt(100) + 1; 
        location = random.nextInt(5) + 1; 
        address = (random.nextInt(7) + 4) + " th avenue";

        System.out.println("[GENERATOR] CREATED ORDER:" + location + " " + address);

    }

    @Override
    public int getLocation() {
        return location; 
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getAddress() {
        return address;
    }


}
