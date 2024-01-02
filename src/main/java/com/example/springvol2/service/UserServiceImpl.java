package com.example.springvol2.service;

import com.example.springvol2.domain.User;
import com.example.springvol2.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> updateUser(Long id, User user) {
        return userRepository.existsById(id) ? Optional.of(userRepository.save(user)) : Optional.empty();
    }

    @Transactional
    public boolean deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);
        if (exists) userRepository.deleteById(id);
        return exists;
    }
}
