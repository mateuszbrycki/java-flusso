package com.example.database;

import com.example.entity.File;
import com.example.entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 31.12.2015.
 */
public class DatabaseServiceImpl implements DatabaseService {

private final String Driver = "com.mysql.jdbc.Driver";
private Connection connection = null;
private final String URL = "jdbc:mysql://localhost:3306/";

    public DatabaseServiceImpl()
    {
        //ładowanie sterownika do bazy danych
        try {
            Class.forName(Driver).newInstance();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Cannot load database driver");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //tworzenie połaczenia
        try {
            connection = DriverManager.getConnection(this.URL, "root", "");
        }
        catch (SQLException e) {
            System.out.println("Cannot connect to database");
        }
    }

    @Override
    public Boolean loginUser(String mail, String password) throws SQLException {

        Statement st = connection.createStatement();

        String query = "SELECT u.* FROM user u WHERE u.mail = " + mail + " AND u.password = " + password + ";";

        ResultSet resultSet = st.executeQuery(query);

        st.close();

        if(resultSet.next())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void saveUser(User user) throws SQLException {
        Statement st = connection.createStatement();

        String saveUserQuery = "INSERT INTO user ( mail, password, registrationDate) VALUES " +
                "('" + user.getMail() + "', '"+ user.getPassword() + "', CURDATE());";

        st.executeUpdate(saveUserQuery);

        st.close();
    }

    @Override
    public User findUserByMail(String mail) throws SQLException {
        Statement st = connection.createStatement();

        String query = "SELECT u.* FROM user WHERE u.mail = " + mail + ";";

        ResultSet resultSet = st.executeQuery(query);

       return this.mapUserObject(resultSet);
    }

    @Override
    public User findUserById(Integer userId) throws SQLException {
        Statement st = connection.createStatement();

        String query = "SELECT u.* FROM user WHERE u.user_id = " + userId + ";";

        ResultSet resultSet = st.executeQuery(query);

        st.close();

        return this.mapUserObject(resultSet);
    }

    @Override
    public Boolean checkIfMailExists(String mail) throws SQLException {
        Statement st = connection.createStatement();

        String query = "SELECT u.user_id FROM user WHERE u.mail = " + mail + ";";

        ResultSet resultSet = st.executeQuery(query);

        if(resultSet.next())
        {
            return true;
        }

        st.close();

        return false;
    }

    @Override
    public void saveFile(File file) throws SQLException {
        Statement st = connection.createStatement();

        String query = "INSERT INTO file (fk_owner_id, name, extension, size, uploadDate) VALUES " +
                "('" + file.getOwner().getId() + "', '"+ file.getName()+ "', '" + file.getExtension()
                + "', '" + file.getSize() + "', CURDATE());";

        st.executeUpdate(query);

        st.close();
    }

    @Override
    public List<File> findUserFiles(Integer userId) throws SQLException {
        Statement st = connection.createStatement();

        String query = "SELECT f.* FROM file WHERE f.fk_owner_id = " + userId + ";";

        ResultSet resultSet = st.executeQuery(query);

        st.close();

        return this.mapUserFiles(resultSet);
    }

    private User mapUserObject(ResultSet resultSet) throws SQLException {

        if(resultSet.next()) {
            User user = new User();
            user.setId( resultSet.getInt("user_id"));
            user.setMail( resultSet.getString("mail"));
            user.setPassword( resultSet.getString("password"));
            user.setRegistrationDate( resultSet.getDate("registrationDate"));

            return user;
        }

        return null;
    }

    private List<File> mapUserFiles(ResultSet resultSet) throws SQLException {
        List<File> userFiles = new ArrayList<>();

            while(resultSet.next())
            {
                File file = new File();

                    file.setId( resultSet.getInt("file_id"));
                    file.setName( resultSet.getString("name"));
                    file.setExtension( resultSet.getString("extension"));
                    file.setSize( resultSet.getInt("size"));
                    file.setUploadDate( resultSet.getDate("uploadDate"));
                    file.setOwner( this.findUserById( resultSet.getInt("fk_owner_id")));

                userFiles.add(file);
            }

        return userFiles;
    }
}
