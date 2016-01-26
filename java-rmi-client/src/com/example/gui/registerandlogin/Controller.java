package com.example.gui.registerandlogin;

import com.example.entity.User;
import com.example.entity.response.ResponseEntity;
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
    public static User user;
    public UserRepository userRepository = new UserRepository();
    public static ResponseEntity<Boolean, Object> userEntity;

    public void loginButtonClicked() throws Exception {
        userEntity = userRepository.loginUser(loginFromLogin.getText(), passwordFromLogin.getText());
        user = (User)userEntity.getValue();
        if (userEntity.getStatus())
            changeWindowToMain();
        else{
            new AlertBox().display("Login Error", "Your account does not exist, check your account/password again");
        }
    }

    public void registerButtonClicked() throws Exception {
        try {
            MailTextFieldValidator.validate(mailFromRegister);
            PasswordTextFieldValidator.validate(passwordFromRegister);
            PasswordTextFieldValidator.notTheSamePassword(passwordFromRegister, repeatFromRegister);
            user = new User(mailFromRegister.getText(), passwordFromRegister.getText(), new Date());
            userRepository.registerUser(user);
            changeWindowToMain();
        } catch (RegisterException e) {
            AlertBox.display("Something was wrong", "Form was filled not in a proper way");
        }

    }

    public void changeWindowToMain() throws IOException {

        LoginAndRegistrationGUI.window.hide();
        MainGUI mainGUI = new MainGUI();
        System.out.print(loginFromRegister.getText());
        mainGUI.setVisibleLogin(loginFromRegister.getText());
        mainGUI.start(new Stage());
    }
}
