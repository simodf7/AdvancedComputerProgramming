package server;

import interfaces.ICounter;

public class CounterImpl implements ICounter{

    private int count = 0; 

    @Override
    public void setCount(String id, int s) {
        count = s; 
        System.out.println("[SERVER] Set count to " + s );
    }

    @Override
    public int sum(int s) {
        count += s; 
        System.out.println("[SERVER] Summed " + s );
        return count; 
    }

    @Override
    public int increment() {
        count++; 
        System.out.println("[SERVER] Incremented " );
        return count; 
    }
    
}
