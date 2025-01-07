package com.example.lab2.Controller;

import com.example.lab2.Model.UserEntity;
import com.example.lab2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("users/add-user")
    @ResponseBody
    public ResponseEntity addUser() {
        UserEntity user = new UserEntity()
                .builder()
                .name("Ivan")
                .budget(0.0)
                .build();
        UserEntity savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser.getId());
    }
}