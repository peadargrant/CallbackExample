package client;
// RMI Example - callback

import server.CallbackServerInterface;
import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

 // Class represents the object client for a distributed object of class
// CallbackServerImpl, which implements the remote interface
// CallbackServerInterface.  It also accepts callback from the server.
public class CallbackClient {

    public static void main(String args[]) {
        try {
            int RMIPort;
            String hostName;

            Scanner input = new Scanner(System.in);
            System.out.println("Enter the RMIRegistry host name:");
            hostName = input.nextLine();
            System.out.println("Enter the RMIregistry port number:");
            RMIPort = input.nextInt();
            input.nextLine();
            System.out.println("Enter how many seconds to stay registered:");
            int time = input.nextInt();
            input.nextLine();

            // find the remote object and cast it to an interface object
            String registryURL = "rmi://" + hostName + ":" + RMIPort + "/callback";
            CallbackServerInterface h = (CallbackServerInterface) Naming.lookup(registryURL);
            System.out.println("Lookup completed ");
            // call remote method
            System.out.println("Server said " + h.sayHello());

            CallbackClientInterface callbackObj = new CallbackClientImpl();

            // register for callback
            h.registerForCallback(callbackObj);
            System.out.println("Registered for callback.");

            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException ex) { // sleep over
            }

            // unregister for callback
            h.unregisterForCallback(callbackObj);

            System.out.println("Unregistered for callback.");
            
            // Without this line, the client will live forever
            UnicastRemoteObject.unexportObject(callbackObj,true);
            System.out.println("Program concluded.");
            
        } // end try
        catch (Exception e) {
            System.out.println("Exception in CallbackClient: " + e);
        } // end catch
    } //end main
}//end class
