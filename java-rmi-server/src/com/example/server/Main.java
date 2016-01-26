package com.example.server;

import com.example.database.DatabaseService;
import com.example.database.DatabaseServiceImpl;
import com.example.rmi.UserServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
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

    public static Integer ConnectionPort = 8800;
    public static Integer numberOfClients = 8;

    public static void main(String[] args) throws IOException, SQLException {

        ServerSocket serverSocket;

        //start database and rmi service
        DatabaseService databaseServiceImpl = new DatabaseServiceImpl();
        UserServiceImpl userService = new UserServiceImpl(databaseServiceImpl);

        ExecutorService executor = Executors.newFixedThreadPool(Main.numberOfClients);
        List<ClientFutureTask> clientFutureTasks = new ArrayList<ClientFutureTask>();

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
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
}
