package com.example.gui.registerandlogin;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Tomasz Hanusiak on 26.12.2015.
 */
public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(100);
        window.setResizable(false);

        Label label = new LabelFactory().createLabel(message);
        Button yesButton = new ButtonFactory().createButton("Yes");
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        Button noButton = new ButtonFactory().createButton("No");
        noButton.setOnAction(e -> {
            answer = false;
            window.close();

        });

        HBox layout = new HBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);


        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
