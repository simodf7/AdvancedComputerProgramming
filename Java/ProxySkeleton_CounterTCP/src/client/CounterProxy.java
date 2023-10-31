package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import interfaces.ICounter;

public class CounterProxy implements ICounter{

    private String addr;
    private int port;   

    public CounterProxy(String a, int p){
        this.addr = a;
        this.port = p; 
    }

    @Override
    public void setCount(String id, int s) {

        try {
            Socket socket = new Socket(addr, port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("setCount");
            out.writeUTF(id);
            out.writeInt(s);

            in.readUTF();


            socket.close();



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        



    }

    @Override
    public int sum(int s) {
        
        int res = 0 ; 


        try {
            
            Socket socket = new Socket(addr, port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            DataInputStream in = new DataInputStream(socket.getInputStream()); 

            out.writeUTF("sum");
            out.writeInt(s);

            res = in.readInt(); 

            socket.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        


        return res; 



    }

    @Override
    public int increment() {

        int res = 0 ; 


        try {
            
            Socket socket = new Socket(addr, port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            DataInputStream in = new DataInputStream(socket.getInputStream()); 

            out.writeUTF("increment");

            res = in.readInt(); 

            socket.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        


        return res; 

    }
    
    


}   
