package com.example.lab2.Controller;

import com.example.lab2.Model.ExpenseEntity;
import com.example.lab2.Model.IncomeEntity;
import com.example.lab2.Model.UserEntity;
import com.example.lab2.Service.ExpenseService;
import com.example.lab2.Service.IncomeService;
import com.example.lab2.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }


    @GetMapping("/{id}")
    public String getUserById(@PathVariable Integer id, Model model,
                              @RequestParam(required = false) Date startDate,
                              @RequestParam(required = false) Date endDate,
                              @RequestParam(required = false) Boolean sort) {
        if (sort == null) {
            sort = false;
        }
        if (startDate == null || endDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = new Date(calendar.getTimeInMillis());
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            endDate = new Date(calendar.getTimeInMillis());
        }


        List<IncomeEntity> incomes = sort
                ? incomeService.sortByCategory(incomeService.findByUserIdAndDateRange(id, startDate, endDate))
                : incomeService.findByUserIdAndDateRange(id, startDate, endDate);
        List<ExpenseEntity> expenses = sort
                ? expenseService.sortByCategory(expenseService.findByUserIdAndDateRange(id, startDate, endDate))
                : expenseService.findByUserIdAndDateRange(id, startDate, endDate);
        userService.findById(id).ifPresent(user -> {
            model.addAttribute("user", user);
            model.addAttribute("balanceStatus", userService.balanceStatus(user.getBudget()));
        });
        model.addAttribute("incomeForThisTime", incomeService.totalSum(incomes));
        model.addAttribute("expenseForThisTime", expenseService.totalSum(expenses));
        model.addAttribute("incomes", incomes);
        model.addAttribute("expenses", expenses);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sort", sort);
        return "user-details";
    }

    @PostMapping("/{id}/expense/new/create")
    public String createNewExpense(@PathVariable Integer id, @ModelAttribute ExpenseEntity expense) {
        ExpenseEntity savedExpense = new ExpenseEntity()
                .builder()
                .userId(id)
                .date(expense.getDate())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .category(expense.getCategory())
                .build();
        expenseService.save(savedExpense);
        return "redirect:/user/" + id;
    }

    @GetMapping("/{id}/income/new")
    public String newIncome(@PathVariable Integer id, Model model, @ModelAttribute IncomeEntity income) {
        model.addAttribute("income", income);
        userService.findById(id).ifPresent(user -> {
            model.addAttribute("user", user);
        });
        return "create-income";
    }

    @GetMapping("/{id}/expense/new")
    public String newExpense(@PathVariable Integer id, Model model, @ModelAttribute ExpenseEntity expense) {
        model.addAttribute("expense", expense);
        userService.findById(id).ifPresent(user -> {
            model.addAttribute("user", user);
        });
        return "create-expense";
    }

    @PostMapping("/{id}/income/new/create")
    public String createNewIncome(@PathVariable Integer id, @ModelAttribute IncomeEntity income) {
        IncomeEntity savedIncome = new IncomeEntity()
                .builder()
                .userId(id)
                .date(income.getDate())
                .amount(income.getAmount())
                .description(income.getDescription())
                .category(income.getCategory())
                .build();
        incomeService.save(savedIncome);
        return "redirect:/user/" + id;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user, @PathVariable Integer id) {
        userService.findById(id)
                .map(oldUser -> {
                    oldUser.setName(user.getName());
                    oldUser.setBudget(user.getBudget());
                    userService.save(oldUser);
                    return null;
                });
        return ResponseEntity.ok("user is saved");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user) {
        UserEntity newUser = new UserEntity()
                .builder()
                .name(user.getName())
                .budget(user.getBudget())
                .build();
        UserEntity savedUser = userService.save(newUser);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @GetMapping("/{id}/income/all")
    public ResponseEntity<?> getIncome(@PathVariable Integer id) {
        return ResponseEntity.ok(incomeService.findByUserId(id));
    }
}