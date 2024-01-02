package com.example.springvol2.service;


import com.example.springvol2.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    Optional<User> getUserById(Long id);

    User createUser(User user);

    Optional<User> updateUser(Long id, User user);

    boolean deleteUser(Long id);
}
