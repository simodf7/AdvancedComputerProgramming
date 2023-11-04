package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

import sensorsManager.IReading;

public interface IController extends Remote{

    void addActuator(int port) throws RemoteException; 
    boolean sensorRead(IReading reading) throws RemoteException; 
    
}