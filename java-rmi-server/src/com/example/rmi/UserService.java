package com.example.rmi;

import com.example.entity.File;
import com.example.entity.User;
import com.example.entity.response.ResponseEntity;

import java.rmi.Remote;
import java.sql.SQLException;
import java.util.List;

/**
 * UserService interface
 */
public interface UserService extends Remote {

    ResponseEntity<Boolean, Object> loginUser(String mail, String password) throws SQLException;
    ResponseEntity<Boolean, Object> registerUser(User user) throws SQLException;

    List<File> getUserFiles(Integer userId) throws SQLException;

}
