package com.codingtest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MonthlySalarySlip {
    private String name;
    private BigDecimal grossMonthlyIncome;
    private BigDecimal monthlyIncomeTax;
    private BigDecimal netMonthlyIncome;
}
