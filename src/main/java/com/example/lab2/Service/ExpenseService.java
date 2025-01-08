package com.example.lab2.Service;

import com.example.lab2.Model.ExpenseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    Iterable<ExpenseEntity> findAll();

    Optional<ExpenseEntity> findById(Integer id);

    ExpenseEntity save(ExpenseEntity expense);

    void update(ExpenseEntity expense, Double oldAmount);

    String delete(Integer id);

    Iterable<ExpenseEntity> findByUserId(Integer userId);

    List<ExpenseEntity> findByUserIdAndDateRange(Integer userId, Date startDate, Date endDate);

}