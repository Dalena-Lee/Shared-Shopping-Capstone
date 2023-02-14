package com.techelevator.dao;

import com.techelevator.model.User;

import java.security.Principal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIDByUsername(String username);

    boolean create(String username, String password, String role);

    List<String> listUsernames(Principal principal);

    public List<User> getUsersByGroupId(int groupID);

}
