package com.codingtest.unittests;

import com.codingtest.exception.TaxCalculatorException;
import com.codingtest.unittests.tax.TaxCalculatorService;
import com.codingtest.exception.SalarySlipGeneratorException;
import com.codingtest.entity.MonthlySalarySlip;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
public class SalarySlipGeneratorService {
    TaxCalculatorService taxCalculatorService;

    public MonthlySalarySlip generateSalarySlip(String name, String annualSalaryStr) throws TaxCalculatorException, SalarySlipGeneratorException {
        BigDecimal annualSalary = null;
        try {
            annualSalary = new BigDecimal(annualSalaryStr);
        }catch (NumberFormatException e){
            String errMsg = "Invalid value provided for salary. Plz provide only numeric value for salary";
            System.out.println(errMsg); // In real world app this would be logger statement along with exception stack
            throw new SalarySlipGeneratorException(errMsg);
        }


        if(annualSalary == null || annualSalary.signum() <= 0) {
            throw new SalarySlipGeneratorException("Annual salary cannot be negative or zero");
        }

        BigDecimal grossMonthlyIncome = calculateGrossMonthlyIncome(annualSalary);
        BigDecimal monthlyIncomeTax = taxCalculatorService.calculateMonthlyTax(annualSalary);
        BigDecimal netMonthlyIncome = calculateNetMonthlyIncome(grossMonthlyIncome, monthlyIncomeTax);


        return new MonthlySalarySlip(name, grossMonthlyIncome, monthlyIncomeTax, netMonthlyIncome);
    }

    private BigDecimal calculateGrossMonthlyIncome(BigDecimal annualSalary){
        return annualSalary.divide(new BigDecimal(12), 2, RoundingMode.HALF_DOWN);
    }

    private BigDecimal calculateNetMonthlyIncome(BigDecimal grossIncome, BigDecimal incomeTax){
        return grossIncome.subtract(incomeTax);
    }
}
