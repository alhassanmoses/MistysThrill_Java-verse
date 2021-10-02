package com.mistyinc.mistysthrill.dao;

import com.mistyinc.mistysthrill.DataStore;
import com.mistyinc.mistysthrill.entities.User;

import java.util.List;

public class UserDao {
    public List<User> getUsers(){
        return DataStore.getUsers();
    }
}
