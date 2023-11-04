package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import sensorsManager.IReading;

public class ControllerImpl extends UnicastRemoteObject implements IController{

    private Vector<ActuatorProxy> actuators; 

    protected ControllerImpl() throws RemoteException {
        super();
        actuators = new Vector<ActuatorProxy>();
    }

    @Override
    public void addActuator(int port) throws RemoteException {
        ActuatorProxy actuator = new ActuatorProxy(port); 
        actuators.add(actuator);
        System.out.println("[CONTROLLER] Subscription happened");

    }

    @Override
    public boolean sensorRead(IReading reading) throws RemoteException {
        
        boolean check = false;
        String readingAttrs = reading.getType() + " " + reading.getValue(); 

        for(int i=0; i<actuators.size(); i++){
            if (actuators.get(i).execute(readingAttrs)){
                check = true; 
            } 
        }

        return check;
    }




    
}
