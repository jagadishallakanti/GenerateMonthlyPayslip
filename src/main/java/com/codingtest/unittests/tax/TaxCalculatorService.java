package com.codingtest.unittests.tax;

import com.codingtest.exception.TaxCalculatorException;

import java.math.BigDecimal;

public interface TaxCalculatorService {
    public BigDecimal calculateMonthlyTax(BigDecimal annualSalary) throws TaxCalculatorException;
}
