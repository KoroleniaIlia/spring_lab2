package com.example.lab2.Sort.Implementation;

import com.example.lab2.Model.ExpenseEntity;
import com.example.lab2.Sort.SortItemsByCategory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SortExpensesByCategoryImplementation implements SortItemsByCategory<ExpenseEntity> {
    @Override
    public List<ExpenseEntity> sortByCategory(List<ExpenseEntity> expenses) {
        return expenses.stream()
                .sorted(Comparator.comparing(ExpenseEntity::getCategory))
                .collect(Collectors.toList());
    }
}