package com.example.database;

import com.example.entity.File;
import com.example.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mateusz on 31.12.2015.
 */
public interface DatabaseService {

    public Boolean loginUser(String mail, String password) throws SQLException;

    public void saveUser(User user) throws SQLException;

    public User findUserByMail(String mail) throws SQLException;

    public User findUserById(Integer userId) throws SQLException;

    public Boolean checkIfMailExists(String mail) throws SQLException;

    public List<File> findUserFiles(Integer userId) throws SQLException;
}
