package com.example.server;

import com.example.database.DatabaseService;
import com.example.database.DatabaseServiceImpl;
import com.example.entity.UserFile;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Mateusz on 12.01.2016.
 */
public class ClientThread implements Callable<String> {

    private Socket socket = null;
    private Integer ID;
    private Lock lock;
    private BufferedReader bufferedReader;
    private DatabaseService databaseService;
    private Scanner scanner;
    private PrintWriter printWriter;

    private static final String DOWNLOAD = "DOWNLOAD";
    private static final String UPLOAD = "UPLOAD";

    public ClientThread(Socket socket) throws SQLException {

        this.socket = socket;
        this.lock = new ReentrantLock();
        this.databaseService = new DatabaseServiceImpl();
    }

    @Override
    public String call() {
        lock.lock();

        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            scanner = new Scanner(inputStream);
            String command = "";

            if(this.scanner.hasNextLine()) {
                command = scanner.nextLine();
                System.out.println(command);
            }

            if(this.scanner.hasNextLine()) {
                this.ID = Integer.parseInt(this.scanner.nextLine());
                System.out.println(this.ID);
            }

                if(command.equals(ClientThread.DOWNLOAD)) {

                    this.downloadFiles(outputStream, inputStream);

                } else if(command.equals(ClientThread.UPLOAD)) {

                    this.uploadFiles(inputStream, ID);

                }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;
    }

    /**
     * Metoda odpowiedzialna za pobieranie plików z serwera
     * @param outputStream strumień wyjściowy
     * @throws IOException
     */
    private void downloadFiles(OutputStream outputStream, InputStream inputstream) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputstream);

        List<UserFile> filesToDownload = (List<UserFile>) objectInputStream.readObject();
        List<File> files = new ArrayList<>();

            for(UserFile userFile: filesToDownload) {
                files.add(new File(userFile.getName()));
            }

        Packet packet = new Packet(files);

        objectOutputStream.writeObject(packet);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
     * Metoda odpowiedzialna za wysyłanie plików na serwer
     * @param inputStream strumień wejściowy
     * @throws IOException
     */
    private void uploadFiles(InputStream inputStream, Integer ID) throws IOException, ClassNotFoundException, SQLException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        Packet packet = (Packet) objectInputStream.readObject();

        System.out.println("upload after packet");
        List<Packet.FileContent> files = packet.getFileContentList();

        this.saveUploadedFiles(files, ID);

        objectInputStream.close();
    }

    /**
     * Metoda zapisująca wysłane na serwer pliki
     * @param files lista z obiektami klasy FileContent
     * @param ID identyfikator użytkownika
     * @throws IOException
     * @throws SQLException
     */
    private void saveUploadedFiles(List<Packet.FileContent> files, Integer ID) throws IOException, SQLException {

        for(Packet.FileContent fileContent: files) {
            File file = fileContent.getFile();
            byte[] fileBytes = fileContent.getFileBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(file.getName());
            fileOutputStream.write(fileBytes);

            //zapis pliku użytkownika do bazy
            databaseService.saveFile(this.getUserFileObject(file, ID));
        }
    }

    /**
     * Metoda tworząca obiekt klasy UserFile z pomocą obiektu klasy File
     * @param file obiekt klasy File
     * @param ID identyfikator użytkownika
     * @return obiekt klasy UserFile
     * @throws SQLException
     */
    private UserFile getUserFileObject(File file, Integer ID) throws SQLException {
        UserFile userFile = new UserFile();
        userFile.setOwner(databaseService.findUserById(ID));
        userFile.setName(file.getName());
        userFile.setSize((int) file.length());

        return userFile;
    }
}
