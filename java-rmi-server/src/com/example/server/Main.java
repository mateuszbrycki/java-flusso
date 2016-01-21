package com.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mateusz on 12.01.2016.
 */
public class Main {

    public static Integer ConnectionPort = null;
    public static Integer numberOfClients = 8;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(Main.ConnectionPort);

        ExecutorService executor = Executors.newFixedThreadPool(Main.numberOfClients);
        List<ClientFutureTask> clientFutureTasks = new ArrayList<>();

            try {

                serverSocket = new ServerSocket(Main.ConnectionPort);

                while(true) {

                    Socket socket = null;

                    try {
                        socket = serverSocket.accept();

                        ClientFutureTask clientFutureTask = new ClientFutureTask(new ClientThread(socket));
                        clientFutureTasks.add(clientFutureTask);

                        executor.execute(clientFutureTask);
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }

                    Iterator<ClientFutureTask> iterator = clientFutureTasks.iterator();

                        while(iterator.hasNext()) {
                                ClientFutureTask clientFutureTaskResult = iterator.next();

                                try {

                                    if (clientFutureTaskResult.isDone()) {
                                        System.out.println(clientFutureTaskResult.get());
                                        iterator.remove();
                                    }
                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                        }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
}
