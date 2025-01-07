package com.example.lab2.Service.Implementation;

import com.example.lab2.Model.UserEntity;
import com.example.lab2.Repository.UserRepository;
import com.example.lab2.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Iterable<UserEntity> findAll() {
        return userRepository.findAll();
    }
    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
    @Override
    public Optional<UserEntity> findById(Integer id) {
        return userRepository.findById(id);
    }
    @Override
    public String deleteById(Integer id) {
        userRepository.deleteById(id);
        if (!userRepository.existsById(id)) {
            return "User with id " + id + " was deleted";
        } else {
            return "User with id " + id + " wasn't deleted";
        }
    }
}