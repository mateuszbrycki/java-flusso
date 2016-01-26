package com.example.gui.maingui;

import com.example.entity.UserFile;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Brolly on 13.01.2016.
 */
public class ListViewItemDownload extends HBox {
    private Button dowloadButtonItem;
    private UserFile file;
    private Label label;

    static ArrayList<UserFile> listFileToDowload = new ArrayList<>();

    public ListViewItemDownload(UserFile file) {
        super();
        this.file = file;
        listFileToDowload.add(file);
        Image imageFile = new Image("file:java-rmi-client/media/text-x-generic.png");
        label = new Label(file.getName());
        label.setGraphic(new ImageView(imageFile));
        label.getStyleClass().add("blacklabel");
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);
        dowloadButtonItem = new Button("Dowload");
        dowloadButtonItem.setOnAction(event1 -> {
            saveFileChooser();
            findFileToRemove(file);
            Image imageDowload = new Image("file:java-rmi-client/media/downloaded.png");
            dowloadButtonItem.setGraphic(new ImageView(imageDowload));
            dowloadButtonItem.setText("");
            dowloadButtonItem.setDisable(true);
            label.getStyleClass().remove("blacklabel");

        });
        this.getChildren().addAll(label, dowloadButtonItem);
    }


    private void findFileToRemove(UserFile file) {
        ArrayList<UserFile> list = listFileToDowload;
        Iterator<UserFile> it = list.iterator();
        while (it.hasNext()) {
            UserFile temp = it.next();
            if (temp == file)
                it.remove();
        }
    }

    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveFileChooser()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(MainGUI.stage);
    }
}
