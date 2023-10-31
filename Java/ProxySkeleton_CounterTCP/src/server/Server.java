package server;


public class Server {
    
    public static void main(String[] args) {
        
        CounterImpl counter = new CounterImpl(); 
        CounterSkeleton skeleton = new CounterSkeleton(counter, 9000);
        skeleton.runSkeleton(); 
    }
}
