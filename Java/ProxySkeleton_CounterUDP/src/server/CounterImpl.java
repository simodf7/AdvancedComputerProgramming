package server;

public class CounterImpl extends CounterSkeleton {

    private int count = 0; 

    @Override
    public void setCount(String id, int s) {
        System.out.println("Count set to " + id + " by client " + s);
        count = s; 
    }

    @Override
    public int sum(int s) {
        System.out.println("Count summed by " + s);
        count = count + s; 
        return count; 
        
    }

    @Override
    public int increment() {
        
        System.out.println("Count incremented" );
        count++;
        return count; 

    }
    
}
