package server;
// RMI Example - callback

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.Scanner;

public class CallbackServer {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        String registryURL;

        try {
            System.out.println("Enter the RMIregistry port number:");
            int RMIPortNum = input.nextInt();

            startRegistry(RMIPortNum);

            CallbackServerImpl exportedObj = new CallbackServerImpl();
            registryURL = "rmi://localhost:" + RMIPortNum + "/callback";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Callback Server ready.");
        } // end try
        catch (Exception re) {
            System.out.println("Exception in HelloServer.main: " + re);
        } // end catch
    } // end main

    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
        } catch (RemoteException e) {
            // No valid registry at that port.
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
        }
    } // end startRegistry
} // end class
