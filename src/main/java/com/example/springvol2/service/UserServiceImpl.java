package com.example.springvol2.service;

import com.example.springvol2.domain.User;
import com.example.springvol2.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Transactional
    public User updateUser(String email, User user) {
        return userRepository.updateUser(email, user);
    }

    @Transactional
    public int deleteUser(User user) {
        return userRepository.deleteUser(user);
    }
}
