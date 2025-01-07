package com.example.lab2.Controller;

import com.example.lab2.Model.UserEntity;
import com.example.lab2.Repository.UserRepository;
import com.example.lab2.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser() {
        UserEntity user = new UserEntity()
                .builder()
                .name("Illia")
                .budget(0.0)
                .build();
        UserEntity savedUser = userService.save(user);
        if (savedUser != null) {
            return ResponseEntity.ok(savedUser.getName());
        } else {
            return ResponseEntity.ok("error");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user, @PathVariable Integer id) {
        userService.findById(id)
                .map(oldUser -> {
                    oldUser.setName(user.getName());
                    oldUser.setBudget(user.getBudget());
                    userService.save(oldUser);
                    return null;
                });
        return ResponseEntity.ok("user is saved");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }
}