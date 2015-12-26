package com.example.gui.registerandlogin;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Brolly on 26.12.2015.
 */
public class ToolTipMessages {

    public static Tooltip setToolTipError(String message)
    {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(message);
        Image image = new Image("file:java-rmi-client/media/warn.png");
        tooltip.setGraphic(new ImageView(image));
        return tooltip;
    }
}
