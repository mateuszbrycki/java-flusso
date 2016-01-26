package com.example.database;

import com.example.entity.User;
import com.example.entity.UserFile;

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
    private String username = "root";
    private String databasePassword = "";
    private static String databaseName = "flusso_database";

    public DatabaseServiceImpl() throws SQLException {
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
            connection = DriverManager.getConnection(this.URL, this.username, this.databasePassword);
        }
        catch (SQLException e) {
            System.out.println("Cannot connect to database");
        }

        this.chooseDatabase();
    }

    /**
     * Metoda pozwalająca na wybranie bazy danych
     * @throws SQLException
     */
    private void chooseDatabase() throws SQLException {
        Statement st = connection.createStatement();

        st.executeUpdate("USE " + DatabaseServiceImpl.databaseName + ";");
    }

    @Override
    public Boolean loginUser(String mail, String password) throws SQLException {

        Statement st = connection.createStatement();

        String query = "SELECT u.* FROM user u WHERE u.mail = '" + mail + "' AND u.password = '" + password + "';";

        ResultSet resultSet = st.executeQuery(query);

        Boolean resultSetNext = resultSet.next();
         st.close();
        if(resultSetNext)
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

        String query = "SELECT u.* FROM user u WHERE u.mail = '" + mail + "';";

        ResultSet resultSet = st.executeQuery(query);

       return this.mapUserObject(resultSet);
    }

    @Override
    public User findUserById(Integer userId) throws SQLException {
        Statement st = connection.createStatement();

        String query = "SELECT u.* FROM user u WHERE u.user_id = " + userId + ";";

        ResultSet resultSet = st.executeQuery(query);

        st.close();

        return this.mapUserObject(resultSet);
    }

    @Override
    public Boolean checkIfMailExists(String mail) throws SQLException {
        Statement st = connection.createStatement();

        String query = "SELECT u.user_id FROM user u WHERE u.mail = '" + mail + "';";
        ResultSet resultSet = st.executeQuery(query);

        if(resultSet.next())
        {
            return true;
        }

        st.close();

        return false;
    }

    @Override
    public void saveFile(UserFile file) throws SQLException {
        Statement st = connection.createStatement();

        String query = "INSERT INTO file (fk_owner_id, name, size, uploadDate) VALUES " +
                "('" + file.getOwner().getId() + "', '"+ file.getName() + "', '" +
                file.getSize() + "', CURDATE());";

        st.executeUpdate(query);

        st.close();
    }

    @Override
    public List<UserFile> findUserFiles(Integer userId) throws SQLException {
        Statement st = connection.createStatement();

        String query = "SELECT f.* FROM file f WHERE f.fk_owner_id = " + userId + ";";

        ResultSet resultSet = st.executeQuery(query);
        List<UserFile> userFileList = this.mapUserFiles(resultSet);

        st.close();

        return userFileList;
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

    private List<UserFile> mapUserFiles(ResultSet resultSet) throws SQLException {
        List<UserFile> userFiles = new ArrayList<>();

            while(resultSet.next())
            {
                UserFile userFile = new UserFile();

                    userFile.setId( resultSet.getInt("file_id"));
                    userFile.setName( resultSet.getString("name"));
                    userFile.setSize( resultSet.getInt("size"));
                    userFile.setUploadDate( resultSet.getDate("uploadDate"));
                    userFile.setOwner( this.findUserById( resultSet.getInt("fk_owner_id")));

                userFiles.add(userFile);
            }

        return userFiles;
    }
}
