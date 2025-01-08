package com.example.lab2;

import com.example.lab2.Model.ExpenseEntity;
import com.example.lab2.Model.IncomeEntity;
import com.example.lab2.Sort.Implementation.SortExpensesByCategoryImplementation;
import com.example.lab2.Sort.Implementation.SortIncomesByCategoryImplementation;
import com.example.lab2.Sort.SortItemsByCategory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {

        SpringApplication.run(Lab2Application.class, args);
    }

    @Bean
    public SortItemsByCategory<ExpenseEntity> sortExpensesByCategory() {
        return new SortExpensesByCategoryImplementation();
    }
    @Bean
    public SortItemsByCategory<IncomeEntity> sortIncomesByCategory() {
        return new SortIncomesByCategoryImplementation();
    }

}
