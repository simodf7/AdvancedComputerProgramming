package client;

import interfaces.ICounter;

public class Client {
    
    public static void main(String[] args) {
        
        ICounter counter = new CounterProxy(); 

        int x = 0; 
        System.out.println("set count to 10");
        counter.setCount("user-ACP", 10);

        System.out.println("sum 15 to count" );
        x = counter.sum(15); 
        System.out.println("current count: " + x );

        System.out.println("increment count" );
        x = counter.increment();
        System.out.println("current count: " + x );

    }

}
