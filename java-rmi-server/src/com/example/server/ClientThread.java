package com.example.server;

import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Mateusz on 12.01.2016.
 */
public class ClientThread implements Callable<String> {

    private Socket socket = null;

    public ClientThread(Socket socket){

        this.socket = socket;
    }

    @Override
    public String call() throws Exception {

        return null;
    }
}
