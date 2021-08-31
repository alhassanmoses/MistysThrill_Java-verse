package com.mistyinc.mistysthrill.dao;

import com.mistyinc.mistysthrill.DataStore;
import com.mistyinc.mistysthrill.entities.User;

public class UserDao {
    public User[] getUsers(){
        return DataStore.getUsers();
    }
}
