package com.example.springvol2.service;

import com.example.springvol2.domain.User;
import com.example.springvol2.repository.UserRepository;
import com.example.springvol2.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
        if (userRepository.existsByEmail(user.getEmail())) {
            return user;
        }

        User newUser = User.builder()
            .email(user.getEmail())
            .password(passwordEncoder.encode(user.getPassword()))
            .build();
        return userRepository.save(newUser);
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

    @Transactional
    public String authenticate(User userIn) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userIn.getEmail(),
                        userIn.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return jwtUtil.generateToken(user.getUsername());
    }
}
