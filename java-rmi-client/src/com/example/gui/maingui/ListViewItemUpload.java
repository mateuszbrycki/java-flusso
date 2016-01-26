package com.example.gui.maingui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Brolly on 10.01.2016.
 */
public class ListViewItemUpload extends HBox {
    private Button deleteButtonItem;
    private File file;
    private Label label;

    static ArrayList<File> listFileToSend = new ArrayList<>();

    public ListViewItemUpload(File file) {
        super();
        this.file = file;
        listFileToSend.add(file);
        Image imageFile = new Image("file:java-rmi-client/media/text-x-generic.png");
        label = new Label(file.getAbsolutePath());
        label.setGraphic(new ImageView(imageFile));
        label.getStyleClass().add("blacklabel");
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);
        deleteButtonItem = new Button("Delete");
        deleteButtonItem.setOnAction(event -> {
            findFileToRemove(file);
            Image imageDeleted = new Image("file:java-rmi-client/media/file_delete.png");
            deleteButtonItem.setGraphic(new ImageView(imageDeleted));
            deleteButtonItem.setText("");
            deleteButtonItem.setDisable(true);
            label.getStyleClass().remove("blacklabel");

        });
        this.getChildren().addAll(label, deleteButtonItem);
    }
    
    private void findFileToRemove(File file)
    {
        ArrayList<File> list = listFileToSend;
        Iterator<File> it = list.iterator();
        while (it.hasNext())
        {
            File temp = it.next();
            if (temp == file)
                it.remove();
        }
    }


}
