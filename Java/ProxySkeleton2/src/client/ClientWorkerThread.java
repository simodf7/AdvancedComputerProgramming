package client;


import magazzino.*;

public class ClientWorkerThread extends Thread{

    private IMagazzino magazzino;
    private int tipo; 
    private String[] article_type;

    public ClientWorkerThread(int i, IMagazzino m){
        magazzino = m;
        tipo = i;
        article_type = new String[2];
        article_type[0] = "laptop";
        article_type[1] = "smartphone";
    }

    public void run(){

        int attesa = 0;
        int id;
        String articolo;


        if(tipo %2 == 0){

            System.out.println("[THREAD " + tipo + "] A generato");

            for(int i=0; i<3; i++){
                attesa = (int)(Math.random()*2 +2);
                articolo = article_type[(int)(Math.random()*2)];
                id = (int)(Math.random()*99 +1);
                try {
                    Thread.sleep(attesa);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                magazzino.deposita(articolo,id);
                System.out.println("[THREAD " + tipo + "] B deposito articolo:" + articolo + " id:" + id);
            }
            
        }
        else{

            System.out.println("[THREAD " + tipo + "] B generato");

            for(int i=0; i<3; i++){
                attesa = (int)(Math.random()*2 +2);
                articolo = article_type[(int)(Math.random()*2)];
                try {
                    Thread.sleep(attesa);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                id = magazzino.preleva(articolo);

                System.out.println("[THREAD " + tipo + "] B prelevato articolo:" + articolo + " id:" + id);
            }
        }
        
    
    }

}