package com.example.gui.registerandlogin.validation;

import com.example.gui.registerandlogin.ToolTipMessages;
import com.example.gui.registerandlogin.Utils;
import com.example.gui.registerandlogin.validation.exception.PasswordException;
import javafx.scene.control.PasswordField;

/**
 * Created by Brolly on 26.12.2015.
 */
public class PasswordTextFieldValidator {

    public static void validate(PasswordField passwordField) throws PasswordException {
        if (passwordField != null) {
            int length = passwordField.getLength();

            if (length < 5) {
                Utils.removeAllStyle(passwordField);
                passwordField.setTooltip(ToolTipMessages.setToolTipError("Password should have at least 6 characters"));
                passwordField.getStyleClass().add("bad");
                throw new PasswordException("Your password is to short");
            } else if (length < 6) {
                Utils.removeAllStyle(passwordField);
                passwordField.getStyleClass().add("med");
            } else if (length < 8) {
                Utils.removeAllStyle(passwordField);
                passwordField.getStyleClass().add("good");
            } else if (length < 10) {
                Utils.removeAllStyle(passwordField);
                passwordField.getStyleClass().add("best");
            }
        }
    }

    public static void notTheSamePassword(PasswordField password, PasswordField repeat) throws PasswordException {
        Utils.removeAllStyle(repeat);
        if (password != null && repeat != null) {
            if (!password.getText().equals(repeat.getText())) {
                Utils.removeAllStyle(repeat);
                repeat.setTooltip(ToolTipMessages.setToolTipError("Passwords are not the same"));
                repeat.getStyleClass().add("bad");
                throw new PasswordException("Your passwords are not the same");

            } else {
                Utils.removeAllStyle(repeat);
                repeat.getStyleClass().add("best");
            }
        }
    }


}
