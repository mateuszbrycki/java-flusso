package com.example.gui.registerandlogin;


import javafx.scene.control.Button;

/**
 * Created by Tomasz Hanusiak on 22.12.2015.
 */
public class ButtonFactory
{
        public Button createButton(String displayMessage) {
            Button button = new Button(displayMessage);
            return button;
        }
}
