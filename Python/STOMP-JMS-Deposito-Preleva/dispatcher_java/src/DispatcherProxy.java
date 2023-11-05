import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class DispatcherProxy implements IDispatcher{

    private int port; 

    public DispatcherProxy(int port) {
        this.port = port; 
    }

    @Override
    public void deposita(int id) {

        try {
            Socket socket = new Socket("127.0.0.1", port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 

            out.writeUTF("deposita-" + id);

            String result = in.readLine(); 

            socket.close();




        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        


    }

    @Override
    public int preleva() {

        String result = null; 

        try {
            Socket socket = new Socket("127.0.0.1", port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 

            out.writeUTF("preleva");

            result = in.readLine(); 

            socket.close();




        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        return Integer.valueOf(result); 

    }

}
