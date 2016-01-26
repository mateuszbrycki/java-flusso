package com.example.rmi;

import com.example.entity.UserFile;
import com.example.entity.User;
import com.example.entity.response.ResponseEntity;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Class should be used to call RMI server. Each call is ran in new thread (Java specification).
 */
public class UserRepository {

    private static UserService userService = null;
    private static String SERVER_ADDRES = "localhost";

    /**
     * Calls server to check if user passed good mail and password
     * @param mail
     * @param password
     * @return information if user should be logged in
     * @throws Exception
     */
    public ResponseEntity<Boolean, Object> loginUser(String mail, String password) throws Exception{
        return UserRepository.getUserService().loginUser(mail, password);
    }

    /**
     * Calls server to register new user
     * @param user User entity
     * @return information if user account has been successfully created
     * @throws Exception
     */
    public ResponseEntity<Boolean, Object> registerUser(User user) throws Exception {
        return UserRepository.getUserService().registerUser(user);
    }

    /**
     * Calls server to get user files list
     * @param userId user id
     * @return user files list
     * @throws Exception
     */
    public List<UserFile> getUserFiles(Integer userId) throws Exception {
        return UserRepository.getUserService().getUserFiles(userId);
    }

    public static void loadClientPolicy() {
        System.getProperty("user.dir");
        System.out.print("Client");
        System.setProperty("java.security.policy", "client.policy");
    }

    /**
     * Connects to RMI server
     * @return user service instance to call remote methods
     * @throws Exception
     */
    private static UserService getUserService() throws Exception {
        loadClientPolicy();
        if(UserRepository.userService == null) {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            Registry registry = LocateRegistry.getRegistry("localhost");
            UserRepository.userService = (UserService) registry.lookup("rmiserver");
        }

        return UserRepository.userService;
    }
}
