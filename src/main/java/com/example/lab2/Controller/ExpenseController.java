package com.example.lab2.Controller;

import com.example.lab2.Model.ExpenseEntity;
import com.example.lab2.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Controller
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/{id}")
    public String getAllExpenses(@PathVariable Integer id, Model model) {
        expenseService.findById(id).ifPresent(expense -> {
            model.addAttribute("expense", expense);
        });
        return "expense-details";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestParam Integer userId) {
        Date date = Date.valueOf(LocalDate.now());
        ExpenseEntity expense = new ExpenseEntity()
                .builder()
                .userId(userId)
                .date(date)
                .amount(100.0)
                .description("Expense description")
                .build();
        return ResponseEntity.ok(expenseService.save(expense));
    }

    @PostMapping("/{id}/edit")
    public String editExpense(@PathVariable Integer id, @ModelAttribute ExpenseEntity expense) {
        expenseService.findById(id)
                .map(oldExpense -> {
                    oldExpense.setAmount(expense.getAmount());
                    oldExpense.setDescription(expense.getDescription());
                    oldExpense.setCategory(expense.getCategory());
                    oldExpense.setDate(expense.getDate());
                    expenseService.save(oldExpense);
                    return null;
                });
        return expenseService.findById(id)
                .map(incomeUser -> "redirect:/user/" + incomeUser.getUserId())
                .orElse("redirect:/user/all");
    }

    @PostMapping("/{id}/delete")
    public String editExpense(@PathVariable Integer id) {
        String userId = String.valueOf(expenseService.findById(id).get().getUserId());
        expenseService.delete(id);
        return "redirect:/user/" + userId;
    }
}