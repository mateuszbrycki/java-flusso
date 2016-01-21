package com.example.gui.registerandlogin;

import javafx.scene.control.Label;

/**
 * Created by Brolly on 23.12.2015.
 */
public class LabelFactory {

    public Label createLabel(String displayMessage)
    {
        Label label = new Label(displayMessage);
        return label;
    }

}
