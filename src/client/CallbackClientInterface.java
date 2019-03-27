package client;
// RMI Example - callback

import java.rmi.*;

public interface CallbackClientInterface extends java.rmi.Remote {

  // remote method invoked by a callback server to make a callback to an client
    // which implements this interface.
    // @param message - a string containing information for the
    //                  client to process upon being called back.
    public String notifyMe(String message) throws java.rmi.RemoteException;

} // end interface
