package com.example.lab2.Service.Implementation;

import com.example.lab2.Model.ExpenseEntity;
import com.example.lab2.Repository.ExpenseRepository;
import com.example.lab2.Service.ExpenseService;
import com.example.lab2.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseServiceImplementation implements ExpenseService {
    private ExpenseRepository expenseRepository;
    private UserService userService;

    public ExpenseServiceImplementation(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;

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
        userService.findById(expense.getUserId()).map(user -> {
            user.setBudget(user.getBudget() - expense.getAmount());
            userService.save(user);
            return null;
        });
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
    public Iterable<ExpenseEntity> findByUserId(Integer userId) {
        return expenseRepository.findByUserId(userId);
    }
}