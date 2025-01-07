package com.example.lab2.Service;

import com.example.lab2.Model.ExpenseEntity;

import java.util.Optional;

public interface ExpenseService {
    Iterable<ExpenseEntity> findAll();

    Optional<ExpenseEntity> findById(Integer id);

    ExpenseEntity save(ExpenseEntity expense);

    String delete(Integer id);

    Iterable<ExpenseEntity> findByUserId(Integer userId);
}