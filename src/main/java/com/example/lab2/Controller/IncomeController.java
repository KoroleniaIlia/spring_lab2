package com.example.lab2.Controller;

import com.example.lab2.Model.IncomeEntity;
import com.example.lab2.Service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/income")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;

    @GetMapping("/{id}")
    public String getIncome(@PathVariable Integer id, Model model) {
        incomeService.findById(id).ifPresent(income -> {
            model.addAttribute("income", income);
        });
        return "income-details";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addIncome(@RequestParam Integer userId) {
        Date date = Date.valueOf(LocalDate.now());
        IncomeEntity incomeEntity = new IncomeEntity()
                .builder()
                .userId(userId)
                .description("lorem ipsum sim dolor amet as;ldjasop da[psik d[aspid[op ais dp[sai")
                .amount(1000.0)
                .date(date)
                .category("Супер гроші")
                .build();
        return ResponseEntity.ok(incomeService.save(incomeEntity));
    }

    @PostMapping("/{id}/edit")
    public String editExpense(@PathVariable Integer id, @ModelAttribute IncomeEntity income) {
        incomeService.findById(id)
                .map(oldIncome -> {
                    oldIncome.setAmount(income.getAmount());
                    oldIncome.setDescription(income.getDescription());
                    oldIncome.setCategory(income.getCategory());
                    oldIncome.setDate(income.getDate());
                    incomeService.save(oldIncome);
                    return null;
                });
        return incomeService.findById(id)
                .map(incomeUser -> "redirect:/user/" + incomeUser.getUserId())
                .orElse("redirect:/user/all");
    }

    @PostMapping("/{id}/delete")
    public String editExpense(@PathVariable Integer id) {
        String userId = String.valueOf(incomeService.findById(id).get().getUserId());
        incomeService.delete(id);
        return "redirect:/user/" + userId;
    }
}