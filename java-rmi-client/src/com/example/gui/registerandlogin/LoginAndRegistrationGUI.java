package com.example.gui.registerandlogin;/**
 * Created by Brolly on 22.12.2015.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginAndRegistrationGUI extends Application implements EventHandler<ActionEvent> {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Flusso");
        window.setOnCloseRequest(e->{
            e.consume();
            closeProgram();
        });

        Scene scene = new Scene(root, 600, 350);
        window.setScene(scene);
        Utils.getSource(scene, "gui.css");
        window.show();

    }

    private void closeProgram()
    {
        boolean answer = ConfirmBox.display("Exit", "Sure you want to exit?");
        if(answer)
            window.close();

    }

    @Override
    public void handle(ActionEvent event) {

    }
}
