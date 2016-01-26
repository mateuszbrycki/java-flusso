package com.example.server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 24.01.2016.
 */
public class Packet implements Serializable {
    private List<FileContent> files = new ArrayList<>();

    public Packet(List<File> filesToSend) throws IOException {
        this.initFileContentList(filesToSend);
    }

    public List<FileContent> getFileContentList() {
        return this.files;
    }

    /**
     * Tworzy listę z obiektami klasy FileContent
     * @param listOfFiles lista plików do wysłania
     * @throws IOException
     */
    private void initFileContentList(List<File> listOfFiles) throws IOException {
        for(File file: listOfFiles){
            this.files.add(new FileContent(file));
        }
    }

    /**
     * Klasa wewnętrzna w której przechowywane są:
     * informacje o pliku (obiekt klasy File)
     * zawartość pliku (tablica byte[])
     */
    public class FileContent implements Serializable {
        private byte[] fileBytes;
        private File file;

        public FileContent(File file) throws IOException {
            this.file = file;
            this.fileBytes = this.getFileBytes(file);
        }

        public byte[] getFileBytes() {
            return this.fileBytes;
        }

        public File getFile() {
            return this.getFile();
        }

        /**
         * Metoda dzięki której można pobrać zawartość danego pliku
         * @param file
         * @return zawartość pliku
         * @throws IOException
         */
        private byte[] getFileBytes(File file) throws IOException {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            byte[] fileBytes = new byte[(int) file.length()];
            dataInputStream.readFully(fileBytes);

            return fileBytes;
        }
    }
}
