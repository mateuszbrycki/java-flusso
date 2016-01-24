package com.example.server;

import java.util.concurrent.FutureTask;

/**
 * Created by Mateusz on 12.01.2016.
 */
public class ClientFutureTask extends FutureTask {

    public ClientFutureTask(ClientThread clientThread) {

        super(clientThread);
    }
}
