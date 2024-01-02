package com.example.springvol2.controller;

import com.example.springvol2.dto.UserDTO;
import com.example.springvol2.mapper.UserMapper;
import com.example.springvol2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @Autowired
    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(
                userService.getUsers()
                        .stream()
                        .map(mapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUsersByEmail(@PathVariable Long id) {
        return ResponseEntity.ok(
                mapper.toDTO(userService.getUserById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
        );
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.toDTO(userService.createUser(mapper.toEntity(user)))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO user) {
        user.setId(id);
        return ResponseEntity.ok(
                mapper.toDTO(userService.updateUser(id, mapper.toEntity(user))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok(true);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
