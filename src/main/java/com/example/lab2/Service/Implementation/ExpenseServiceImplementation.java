package com.example.lab2.Service.Implementation;

import com.example.lab2.Model.ExpenseEntity;
import com.example.lab2.Repository.ExpenseRepository;
import com.example.lab2.Service.ExpenseService;

import java.util.Optional;
public class ExpenseServiceImplementation implements ExpenseService {
    private ExpenseRepository expenseRepository;
    public ExpenseServiceImplementation(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }
    @Override
    public Iterable<ExpenseEntity> findAll() {
        return expenseRepository.findAll();
    }
    @Override
    public Optional<ExpenseEntity> findById(Integer id) {
        return expenseRepository.findById(id);
    }
    @Override
    public ExpenseEntity save(ExpenseEntity expense) {
        return expenseRepository.save(expense);
    }
    @Override
    public String delete(Integer id) {
        expenseRepository.deleteById(id);
        if (!expenseRepository.existsById(id)) {
            return "Expense was deleted";
        } else {
            return "Expense wasn't deleted";
        }
    }
    @Override
    public Iterable<ExpenseEntity> findByUserID(Integer userId) {
        return expenseRepository.findByUserId(userId);
    }
}