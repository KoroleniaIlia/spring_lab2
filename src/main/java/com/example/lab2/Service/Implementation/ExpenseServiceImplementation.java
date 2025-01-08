package com.example.lab2.Service.Implementation;

import com.example.lab2.Model.ExpenseEntity;
import com.example.lab2.Repository.ExpenseRepository;
import com.example.lab2.Service.ExpenseService;
import com.example.lab2.Service.UserService;
import com.example.lab2.Sort.SortItemsByCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImplementation implements ExpenseService {
    private ExpenseRepository expenseRepository;
    private UserService userService;
    private SortItemsByCategory<ExpenseEntity> categorySorter;


    @Autowired
    public void setExpenseRepository(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCategorySorter(@Qualifier("sortExpensesByCategory") SortItemsByCategory<ExpenseEntity> categorySorter) {
        this.categorySorter = categorySorter;
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
        expense.setCategory(Character.toUpperCase(expense.getCategory().charAt(0))
                + expense.getCategory().substring(1).toLowerCase());
        return expenseRepository.save(expense);
    }

    @Override
    public Double totalSum(List<ExpenseEntity> expenses) {
        return expenses.stream().mapToDouble(ExpenseEntity::getAmount).sum();
    }

    @Override
    public List<ExpenseEntity> sortByCategory(List<ExpenseEntity> expenses) {
        return categorySorter.sortByCategory(expenses);
    }

    @Override
    public void update(ExpenseEntity expense, Double oldAmount) {
        userService.findById(expense.getUserId()).ifPresent(user -> {
            user.setBudget(user.getBudget() + oldAmount - expense.getAmount());
            userService.save(user);
        });
        expenseRepository.save(expense);
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

    @Override
    public List<ExpenseEntity> findByUserIdAndDateRange(Integer userId, Date startDate, Date endDate) {
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .sorted((expense1, expense2) -> expense2.getDate().compareTo(expense1.getDate()))
                .collect(Collectors.toList());
    }
}