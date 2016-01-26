package com.example.connection;

import com.example.entity.UserFile;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mateusz on 25.01.2016.
 */
public class ConnectionThread implements Callable<String> {

    private static final String DOWNLOAD = "DOWNLOAD";
    private static final String UPLOAD = "UPLOAD";
    private List<UserFile> userFiles;
    private List<File> files;
    private Integer userID;
    private String command;
    private Socket socket;

    /**
     * Konstruktor do pobierania plików
     * @param command komenda
     * @param userFiles pliki do pobrania
     * @param userID id użytkownika
     */
    private ConnectionThread(String command, List<UserFile> userFiles, Integer userID) {
        this.command = command;
        this.userFiles = userFiles;
        this.userID = userID;
    }

    /**
     * Konstruktor do wysyłania plików
     * @param files pliki do wysłania
     * @param command komenda
     * @param userID id użytkownika
     */
    private ConnectionThread(List<File> files, String command, Integer userID) {
        this.files = files;
        this.command = command;
        this.userID = userID;
    }

    public String call() {

        try {

                this.socket = new Socket(InetAddress.getLocalHost(), Main.ConnectionPort);
                InputStream inputStream = this.socket.getInputStream();
                OutputStream outputStream = this.socket.getOutputStream();

                System.out.println("Initializing objectOutputStream.");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                System.out.println("Initializing objectInputStream.");
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                System.out.println("Sending command.");
                //wysłanie polecenia i id użytkownika
                objectOutputStream.writeObject(this.command);
                objectOutputStream.flush();

                System.out.println("Sending user id.");
                objectOutputStream.writeInt(this.userID);
                objectOutputStream.flush();

                if (command.equals(DOWNLOAD)) {

                    objectOutputStream.writeObject(this.userFiles);

                    Packet packet = (Packet) objectInputStream.readObject();
                    List<Packet.FileContent> downloadedFiles = packet.getFileContentList();

                    this.saveDownloadedFiles(downloadedFiles);



                } else if (this.command.equals(UPLOAD)) {

                    System.out.println("Files list in upload branch: " + this.files.size());

                    Packet packet = new Packet(this.files);

                    objectOutputStream.writeObject(packet);
                    objectOutputStream.flush();
                }

            objectOutputStream.close();
            objectInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Metoda odpowiedzialna za pobieranie plików z serwera
     * @param userFiles
     * @param userID
     */
    public static void downloadFiles(List<UserFile> userFiles, Integer userID) {
        ConnectionThread connectionThread = new ConnectionThread("DOWNLOAD", userFiles, userID);

        ConnectionFutureTask connectionFutureTask = new ConnectionFutureTask(connectionThread);

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(connectionFutureTask);
    }

    /**
     * Metoda odpowiedzialna za wysyłanie plików na serwer
     * @param files
     * @param userID
     */
    public static void uploadFiles(List<File> files, Integer userID) {
        ConnectionThread connectionThread = new ConnectionThread(files, "UPLOAD", userID);

        ConnectionFutureTask connectionFutureTask = new ConnectionFutureTask(connectionThread);

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(connectionFutureTask);
    }

    /**
     * Metoda zapisująca pliki pobrane z serwera
     * @param files lista z obiektami klasy FileContent
     * @throws IOException
     */
    private void saveDownloadedFiles(List<Packet.FileContent> files) throws IOException {

        for(Packet.FileContent fileContent: files) {
            File file = fileContent.getFile();
            byte[] fileBytes = fileContent.getFileBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(file.getName());
            fileOutputStream.write(fileBytes);
        }
    }
}
