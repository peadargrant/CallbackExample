package server;
// RMI Example - callback

import client.CallbackClientInterface;
import server.CallbackServerInterface;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

    private ArrayList clientList;

    public CallbackServerImpl() throws RemoteException {
        super();
        clientList = new ArrayList();
    }

    public String sayHello() throws java.rmi.RemoteException {
        return ("hello");
    }

    public synchronized void registerForCallback(
            CallbackClientInterface callbackClientObject)
            throws java.rmi.RemoteException {
        // store the callback object into the arraylist
        if (!(clientList.contains(callbackClientObject))) {
            clientList.add(callbackClientObject);
            System.out.println("Registered new client ");

            // perform the callbacks to all clients that registered for callback
            // this is the event in this example
            // might be user enters room in chat application, etc.
            doCallbacks();
        } // end if
    }

// remote method allows an object client to cancel its registration for callback
    public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject)
            throws java.rmi.RemoteException 
    {
        if (clientList.remove(callbackClientObject)) {
            System.out.println("Unregistered client ");
            doCallbacks();
        } else {
            System.out.println("unregister: client wasn't registered.");
        }
    }

    private synchronized void doCallbacks() throws java.rmi.RemoteException {
        // make callback to each registered client
        System.out.println("**************************************\n"
                + "Callbacks initiated ---");
        for (int i = 0; i < clientList.size(); i++) {
            System.out.println("doing " + i + "-th callback\n");
            // convert the list object to a callback object
            CallbackClientInterface nextClient = (CallbackClientInterface) clientList.get(i);

            // invoke the callback method
            nextClient.notifyMe("Number of registered clients = " + clientList.size());
        }// end for

        System.out.println("********************************\n"
                + "Server completed callbacks ---");
    } // doCallbacks
}// end CallbackServerImpl class
