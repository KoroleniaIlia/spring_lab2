package com.example.lab2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private Double amount;
    private String description;
    private String category;
    private Date date;

}