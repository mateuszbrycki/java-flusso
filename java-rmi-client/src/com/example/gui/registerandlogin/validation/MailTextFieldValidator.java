package com.example.gui.registerandlogin.validation;


import com.example.gui.registerandlogin.ToolTipMessages;
import com.example.gui.registerandlogin.Utils;
import javafx.scene.control.TextField;

/**
 * Created by Brolly on 26.12.2015.
 */
public class MailTextFieldValidator {

    public static void validate(TextField mailField) {
        Utils.removeAllStyle(mailField);
        if (mailField != null)
            if (!mailField.getText().contains("@") || mailField.getText().isEmpty())

            {
                Utils.removeAllStyle(mailField);
                mailField.setTooltip(ToolTipMessages.setToolTipError("Please fill in your email correctly"));
                mailField.getStyleClass().add("bad");
            }


    }

}
