package server;


public class Server {
    
    public static void main(String[] args) {

        ServerImpl skeleton = new ServerImpl(Integer.valueOf(args[0]));
        skeleton.runSkeleton();

    }
}
