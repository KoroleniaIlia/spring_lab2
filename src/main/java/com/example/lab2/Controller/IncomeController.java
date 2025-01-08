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


    @PostMapping("/{id}/edit")
    public String editIncome(@PathVariable Integer id, @ModelAttribute IncomeEntity income) {
        incomeService.findById(id)
                .map(oldIncome -> {
                    Double oldAmount = oldIncome.getAmount();
                    oldIncome.setAmount(income.getAmount());
                    oldIncome.setDescription(income.getDescription());
                    oldIncome.setCategory(income.getCategory());
                    oldIncome.setDate(income.getDate());
                    incomeService.update(oldIncome, oldAmount);
                    return null;
                });
        return incomeService.findById(id)
                .map(incomeUser -> "redirect:/user/" + incomeUser.getUserId())
                .orElse("redirect:/user/all");
    }

    @PostMapping("/{id}/delete")
    public String deleteIncome(@PathVariable Integer id) {
        String userId = String.valueOf(incomeService.findById(id).get().getUserId());
        incomeService.delete(id);
        return "redirect:/user/" + userId;
    }

    /*@PostMapping("/add")
    public ResponseEntity<?> addIncome(@ModelAttribute IncomeEntity income) {
        Date date = Date.valueOf(LocalDate.now());
        IncomeEntity incomeEntity = new IncomeEntity()
                .builder()
                .userId(income.getUserId())
                .description(income.getDescription())
                .amount(income.getAmount())
                .date(income.getDate())
                .category(income.getCategory())
                .build();
        return ResponseEntity.ok(incomeService.save(incomeEntity));
    }*/
}