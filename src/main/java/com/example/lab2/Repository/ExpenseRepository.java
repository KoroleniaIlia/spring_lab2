package com.example.lab2.Repository;


import com.example.lab2.Model.ExpenseEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface ExpenseRepository extends CrudRepository<ExpenseEntity, Integer> {
    Iterable<ExpenseEntity> findByUserId(Integer userId);

    List<ExpenseEntity> findByUserIdAndDateBetween(Integer userId, Date start, Date end);
}