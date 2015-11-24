package com.example.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TextService extends Remote {
    String getUpperCase(String text) throws RemoteException;
    String getLowerCase(String text) throws RemoteException;
    String trim(String text) throws RemoteException;
}
