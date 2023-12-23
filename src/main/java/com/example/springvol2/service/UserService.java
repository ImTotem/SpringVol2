package com.example.springvol2.service;


import com.example.springvol2.domain.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserByEmail(String email);

    User createUser(User user);

    User updateUser(String email, User user);

    int deleteUser(User user);
}
