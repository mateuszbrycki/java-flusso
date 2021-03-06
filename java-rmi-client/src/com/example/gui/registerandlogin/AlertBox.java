package com.example.gui.registerandlogin;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Tomasz Hanusiak on 24.12.2015.
 */
public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setResizable(false);

        Label label = new LabelFactory().createLabel(message);
        Button button = new ButtonFactory().createButton("OK");
        button.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setId("blackscene");
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        Utils.getSource(scene, "gui.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
