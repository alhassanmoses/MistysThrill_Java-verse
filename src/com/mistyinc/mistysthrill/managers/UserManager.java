package com.mistyinc.mistysthrill.managers;

import com.mistyinc.mistysthrill.dao.UserDao;
import com.mistyinc.mistysthrill.entities.User;

import java.util.List;

public class UserManager {
    private static UserManager instance = new UserManager();
    private static UserDao dao = new UserDao();

    private UserManager() {

    }

    public static UserManager getInstance() {
        return instance;
    }

    public User createUser(long id, String email, String password, String firstName, String lastName, int gender, String userType) {
        User user = new User();

        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setUserType(userType);

        return user;
    }

    public List<User> getUsers(){
        return dao.getUsers();
    }
}
