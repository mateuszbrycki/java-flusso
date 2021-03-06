package com.example.rmi;

import com.example.database.DatabaseService;
import com.example.entity.User;
import com.example.entity.UserFile;
import com.example.entity.response.ResponseEntity;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * UserService and Callable interface implementation.
 * Class should be ran as thread (Callable) in server main method.
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService, Callable {

    private DatabaseService databaseService;
    private static Integer serverPort = 1099;

    public UserServiceImpl(DatabaseService databaseService) throws RemoteException {
        super(UserServiceImpl.serverPort);
        this.databaseService = databaseService;
    }


    public static void loadServerPolicy() {
        System.getProperty("user.dir");
        System.setProperty("java.security.policy", "server.policy");
    }
    public String call() throws RemoteException {
        loadServerPolicy();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        /*UserService stub = (UserService) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("rmiserver", stub);*/

        try {

            System.out.println("Starting server...");
            Registry reg = LocateRegistry.createRegistry(UserServiceImpl.serverPort);
            reg.rebind("rmiserver", this);
        } catch(RemoteException e) {
            e.printStackTrace();
        }

        return "RMI server stopped.";

    }

    /**
     * Check if user should be logged in.
     * @param mail mail
     * @param password password
     * @return information if user should be logged in
     */
    public ResponseEntity<Boolean, Object> loginUser(String mail, String password) throws SQLException {

        //TODO MBryzik - zapytane do bazy o u�ytkownika z tym has�em i mailem
        if(!databaseService.loginUser(mail, password)) {
            return new ResponseEntity<Boolean, Object>(false, "User authentication failed");
        }

        //TODO MBryzik - zwr�cenie wype�nionej encji u�ytkownika w celu przes�ania do klienta
        User user = databaseService.findUserByMail(mail);

        System.out.println("User " + user.getMail() + " logged.");

        return new ResponseEntity<Boolean, Object>(true, user);

    }

    /**
     * New user registration
     * @param user user entity instance
     * @return information if user account has been successfully created
     */
    public ResponseEntity<Boolean, Object> registerUser(User user) throws SQLException {
        System.out.println("Checking mail " + user.getMail());

        Boolean userMailExists = databaseService.checkIfMailExists(user.getMail());
        System.out.println("Mail checked");

        if(userMailExists) {
            return new ResponseEntity<Boolean, Object>(false, "User with this mail already exists");
        }
        System.out.println("User doesn't exist");

        //saving user to database
        //TODO MBryzik  - zapisanie encji uytkownika do bazy danych
        databaseService.saveUser(user);

        System.out.println("User " + user.getMail() + " saved");
        //client needs user with id - auto increment on database
        User newUser = databaseService.findUserByMail(user.getMail());

        //return user with id to client
        return new ResponseEntity<Boolean, Object>(true, newUser);
    }

    /**
     * Returns user files list
     * @param userId user id
     * @return user files list
     */
    public List<UserFile> getUserFiles(Integer userId) throws SQLException {

        //TODO MBryzik - pobranie i zmapowanie plik�w uzytkownika
        List<UserFile> userFiles = databaseService.findUserFiles(userId);

        return userFiles;
    }

    public User getUserObject(String mail) throws SQLException {
        return databaseService.getUserObject(mail);
    }
}