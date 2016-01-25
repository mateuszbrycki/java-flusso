package com.example.connection;

import java.util.concurrent.FutureTask;

/**
 * Created by Mateusz on 25.01.2016.
 */
public class ConnectionFutureTask extends FutureTask {

    public ConnectionFutureTask(ConnectionThread connectionThread) {

        super(connectionThread);
    }
}
