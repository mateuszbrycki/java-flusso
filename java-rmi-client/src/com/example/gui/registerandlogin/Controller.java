package com.example.gui.registerandlogin;

import com.example.entity.User;
import com.example.gui.maingui.MainGUI;
import com.example.gui.registerandlogin.validation.MailTextFieldValidator;
import com.example.gui.registerandlogin.validation.PasswordTextFieldValidator;
import com.example.gui.registerandlogin.validation.exception.RegisterException;
import com.example.rmi.UserRepository;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

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

    public UserRepository userRepository = new UserRepository();
    public User user;

    public void loginButtonClicked() throws Exception {
//        TODO Sprawdzic zmiany w UserRepository
        userRepository.loginUser(loginFromLogin.getText(), passwordFromLogin.getText());
    }

    public void registerButtonClicked() throws Exception {
        try {
            MailTextFieldValidator.validate(mailFromRegister);
            PasswordTextFieldValidator.validate(passwordFromRegister);
            PasswordTextFieldValidator.notTheSamePassword(passwordFromRegister, repeatFromRegister);
            user = new User(mailFromRegister.getText(), passwordFromRegister.getText(), new Date());
            userRepository.registerUser(user);

        } catch (RegisterException e) {
            AlertBox.display("Something was wrong", "Form was filled not in a proper way");
        }
        changeWindowToMain();

    }

    public void changeWindowToMain() throws IOException {
        LoginAndRegistrationGUI.window.hide();
        MainGUI mainGUI = new MainGUI();
        mainGUI.start(new Stage());
        System.out.print(loginFromRegister.getText());
        mainGUI.setVisibleLogin(loginFromRegister.getText());
    }
}
