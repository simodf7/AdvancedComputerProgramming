package generator; 


public class Generator{


    public static void main(String[] args) {
        
        Thread[] threads = new Thread[3];

        for(int i=0; i<3; i++){
            GeneratorThread t = new GeneratorThread(); 
            t.start();
            threads[i] = t; 
        }


        for(int i=0; i<3; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }



}   