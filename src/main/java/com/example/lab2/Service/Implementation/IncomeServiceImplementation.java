package com.example.lab2.Service.Implementation;

import com.example.lab2.Model.IncomeEntity;
import com.example.lab2.Repository.IncomeRepository;
import com.example.lab2.Service.IncomeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncomeServiceImplementation implements IncomeService {
    private IncomeRepository incomeRepository;

    public IncomeServiceImplementation(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
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
        return incomeRepository.save(income);
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
}