package com.example.lab2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userId;
    private String category;
    private Double amount;
    private String description;
    private Date date;

}