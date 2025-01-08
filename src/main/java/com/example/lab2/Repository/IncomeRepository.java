package com.example.lab2.Repository;


import com.example.lab2.Model.IncomeEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface IncomeRepository extends CrudRepository<IncomeEntity, Integer> {
    Iterable<IncomeEntity> findByUserId(Integer userId);

    List<IncomeEntity> findByUserIdAndDateBetween(Integer userId, Date start, Date end);

}