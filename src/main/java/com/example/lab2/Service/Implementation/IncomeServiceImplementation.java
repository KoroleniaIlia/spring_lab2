package com.example.lab2.Service.Implementation;

import com.example.lab2.Model.IncomeEntity;
import com.example.lab2.Repository.IncomeRepository;
import com.example.lab2.Service.IncomeService;
import com.example.lab2.Service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncomeServiceImplementation implements IncomeService {
    private IncomeRepository incomeRepository;
    private UserService userService;

    public IncomeServiceImplementation(IncomeRepository incomeRepository, UserService userService) {
        this.incomeRepository = incomeRepository;
        this.userService = userService;
    }

    @Override
    public Iterable<IncomeEntity> findAll() {
        return null;
    }

    @Override
    public Optional<IncomeEntity> findById(Integer id) {
        return incomeRepository.findById(id);
    }

    @Override
    public IncomeEntity save(IncomeEntity income) {
        userService.findById(income.getUserId()).map(user -> {
            user.setBudget(user.getBudget() + income.getAmount());
            userService.save(user);
            return null;
        });
        return incomeRepository.save(income);
    }

    @Override
    public void update(IncomeEntity income, Double oldAmount) {
        userService.findById(income.getUserId()).map(user -> {
            user.setBudget(user.getBudget() - oldAmount + income.getAmount());
            userService.save(user);
            return null;
        });
    }

    @Override
    public String delete(Integer id) {
        incomeRepository.deleteById(id);
        if (!incomeRepository.existsById(id)) {
            return "Income was deleted";
        } else {
            return "Income wasn't deleted";
        }
    }

    @Override
    public Iterable<IncomeEntity> findByUserId(Integer userId) {
        return incomeRepository.findByUserId(userId);
    }

    @Override
    public List<IncomeEntity> findByUserIdAndDateRange(Integer userId, Date startDate, Date endDate) {
        return incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .sorted((income1, income2) -> income2.getDate().compareTo(income1.getDate()))
                .collect(Collectors.toList());
    }
}