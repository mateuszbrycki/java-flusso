package com.example.gui.registerandlogin;

import com.example.gui.registerandlogin.validation.MailTextFieldValidator;
import com.example.gui.registerandlogin.validation.PasswordTextFieldValidator;
import javafx.scene.control.PasswordField;

/**
 * Created by Tomasz Hanusiak on 26.12.2015.
 */


public class Controller {
    public javafx.scene.control.TextField loginFromLogin;
    public javafx.scene.control.PasswordField passwordFromLogin;

    public javafx.scene.control.TextField loginFromRegister;
    public javafx.scene.control.TextField  mailFromRegister;
    public javafx.scene.control.PasswordField passwordFromRegister;
    public javafx.scene.control.PasswordField repeatFromRegister;


    public void loginButtonClicked()
    {
        AlertBox.display("Logowanie", "logowanie");
        System.out.println(loginFromLogin.getText());
        System.out.println(passwordFromLogin.getText());
    }

    public void registerButtonClicked()
    {
        AlertBox.display("Rejestracja", "rejestracja");
        MailTextFieldValidator.validate(mailFromRegister);
        PasswordTextFieldValidator.validate(passwordFromRegister);
        PasswordTextFieldValidator.notTheSamePassword(passwordFromRegister, repeatFromRegister);
        System.out.println(loginFromRegister.getText());
        System.out.println(mailFromRegister.getText());
        System.out.println(passwordFromRegister.getText());
        System.out.println(repeatFromRegister.getText());

    }
}
