package com.example.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements TextService {

    public static void main(String[] args) {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {

            TextService service = new Server();
            TextService stub = (TextService) UnicastRemoteObject.exportObject(service, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("rmiserver", stub);

            System.out.println("Server started..");

        } catch (Exception e) {
            System.err.println("Server side exception:");
            e.printStackTrace();
        }
    }

    public String getUpperCase(String text) { return text.toUpperCase(); }
    public String getLowerCase(String text) { return text.toLowerCase(); }
    public String trim(String text) { return text.trim(); }
}
