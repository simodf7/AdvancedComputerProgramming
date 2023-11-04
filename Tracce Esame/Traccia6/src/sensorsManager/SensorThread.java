package sensorsManager;


import java.rmi.RemoteException;

import controller.IController;

public class SensorThread extends Thread {
    
    private IController controller; 

    public SensorThread(IController c){
        controller = c; 
    }

    public void run(){
        Reading reading = new Reading(); 
       

        try {
            boolean value = controller.sensorRead(reading);
            System.err.println("[SENSOR THREAD] Sensor Reading with value: " + value);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }
}
