package com.example.rmi;

import com.example.entity.File;
import com.example.entity.User;
import com.example.entity.response.ResponseEntity;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * UserService and Callable interface implementation.
 * Class should be ran as thread (Callable) in server main method.
 */
public class UserServiceImpl implements UserService, Callable {

    private DatabaseService databaseService;

    public UserServiceImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public String call() throws RemoteException {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        UserService stub = (UserService) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("rmiserver", stub);

        System.out.println("Server started..");

        return "RMI server stopped.";

    }

    /**
     * Check if user should be logged in.
     * @param mail mail
     * @param password password
     * @return information if user should be logged in
     */
    public ResponseEntity<Boolean, Object> loginUser(String mail, String password) {

        //TODO MBryzik - zapytane do bazy o u¿ytkownika z tym has³em i mailem
        if(!databaseService.loginUser()) {
            return new ResponseEntity<Boolean, Object>(false, "User authentication failed");
        }

        //TODO MBryzik - zwrócenie wype³nionej encji u¿ytkownika w celu przes³ania do klienta
        User user = databaseService.findUserByMail(mail);

        return new ResponseEntity<Boolean, Object>(true, user);

    }

    /**
     * New user registration
     * @param user user entity instance
     * @return information if user account has been successfully created
     */
    public ResponseEntity<Boolean, Object> registerUser(User user) {
        Boolean userMailExists = databaseService.checkIfMailExists(user.getMail());

        if(userMailExists) {
            return new ResponseEntity<Boolean, Object>(false, "User with this mail already exists");
        }

        //saving user to database
        //TODO MBryzik  - zapisanie encji uytkownika do bazy danych
        databaseService.saveUser(user);

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
    public List<File> getUserFiles(Integer userId) {

        //TODO MBryzik - pobranie i zmapowanie plików uzytkownika
        List<File> userFiles = databaseService.findUserFiles(userId);

        return userFiles;
    }

}
