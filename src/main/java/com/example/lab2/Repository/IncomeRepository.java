package com.example.lab2.Repository;


import com.example.lab2.Model.IncomeEntity;
import org.springframework.data.repository.CrudRepository;

public interface IncomeRepository extends CrudRepository<IncomeEntity, Integer> {
    Iterable<IncomeEntity> findByUserId(Integer userId);
}