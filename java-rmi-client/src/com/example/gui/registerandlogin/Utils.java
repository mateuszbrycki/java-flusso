package com.example.gui.registerandlogin;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.File;

/**
 * Created by Brolly on 26.12.2015.
 */
public class Utils {
    public static void removeAllStyle(Node n) {
        n.getStyleClass().removeAll("bad", "med", "good", "best");
    }

    public static void setMessage(Label l, String message, Color color) {
        l.setText(message);
        l.setTextFill(color);
        l.setVisible(true);
    }

    public static void getSource(Scene scene, String file)
    {
        File f = new File(file);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
    }


}
