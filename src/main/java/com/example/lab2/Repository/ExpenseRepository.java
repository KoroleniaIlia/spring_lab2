package com.example.lab2.Repository;


import com.example.lab2.Model.ExpenseEntity;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<ExpenseEntity, Integer> {
    Iterable<ExpenseEntity> findByUserId(Integer userId);
}