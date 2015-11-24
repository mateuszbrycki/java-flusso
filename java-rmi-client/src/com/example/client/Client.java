package com.example.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import com.example.server.TextService;
/**
 * @author Mateusz Brycki
 *
 * Klasa klienta
 */

public class Client {

    public static void main(String[] args) throws Exception {

        if(args.length < 1) {
            throw new Exception("Provide hostname");
        }

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        String text = "This Is Default Text";

        try {


            Registry registry = LocateRegistry.getRegistry(args[0]);
            TextService textService = (TextService) registry.lookup("rmiserver");

            System.out.println(textService.getLowerCase(text));
            System.out.println(textService.getUpperCase(text));
            System.out.println(textService.trim(text));

        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
        }

    }
}
