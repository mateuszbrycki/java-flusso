package com.example.gui.maingui;/**
 * Created by Brolly on 22.12.2015.
 */

import com.example.gui.registerandlogin.ConfirmBox;
import com.example.gui.registerandlogin.Utils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainGUI extends Application implements EventHandler<ActionEvent>, Initializable {

    final FileChooser fileChooser = new FileChooser();
    Stage stage;
    @FXML
    Label userName;
    @FXML
    TreeView<File> folderTree;
    @FXML
    ListView<ListViewItemUpload> listViewUpload;
    @FXML
    ListView<ListViewItemUpload> listViewDownload;
    @FXML
    ImageView imageUploadTab;
    @FXML
    ImageView imageDownloadTab;

    ObservableList<ListViewItemUpload> listViewDataUpload;
    static List<File> fileList;

    private Desktop desktop = Desktop.getDesktop();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("main_gui.fxml"));
        stage = primaryStage;
        stage.setTitle("Fusso");
        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        Utils.getSource(scene, "gui.css");
        stage.show();


    }


    private void closeProgram() {
        boolean answer = ConfirmBox.display("Exit", "Sure you want to exit?");
        if (answer)
            stage.close();

    }

    @Override
    public void handle(ActionEvent event) {

    }


    public void logoutButtonClicked(ActionEvent actionEvent) {
    }

    public void sendFileButtonClicked(ActionEvent actionEvent) {
    }

    public void selectFilesButtonClicked(ActionEvent actionEvent) {

        fileList = fileChooser.showOpenMultipleDialog(stage);
        if (fileList != null) {
            for (File file : fileList) {
                listViewDataUpload.add(new ListViewItemUpload(file));
                listViewUpload.setItems(listViewDataUpload);
                listViewUpload.getEditingIndex();
            }
        }

    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            System.out.println("Cannot catch file");

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewDataUpload = FXCollections.observableArrayList();
        imageDownloadTab.setImage(new Image("file:java-rmi-client/media/cloud-download.png"));
        imageUploadTab.setImage(new Image("file:java-rmi-client/media/cloud-upload.png"));
    }



}
