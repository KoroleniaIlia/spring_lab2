package com.example.lab2.Service;

import com.example.lab2.Model.IncomeEntity;

import java.util.Optional;

public interface IncomeService {
    Iterable<IncomeEntity> findAll();

    Optional<IncomeEntity> findById(Integer id);

    IncomeEntity save(IncomeEntity income);

    String delete(Integer id);

    Iterable<IncomeEntity> findByUserId(Integer userId);
}