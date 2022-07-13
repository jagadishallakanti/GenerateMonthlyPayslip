package com.codingtest;

import com.codingtest.exception.TaxCalculatorException;
import com.codingtest.repository.TaxRepository;
import com.codingtest.repository.TaxRepositoryImpl;
import com.codingtest.unittests.SalarySlipGeneratorService;
import com.codingtest.unittests.tax.TaxCalculatorService;
import com.codingtest.exception.SalarySlipGeneratorException;
import com.codingtest.exception.entity.MonthlySalarySlip;
import com.codingtest.unittests.tax.TaxCalculatorServiceImpl;

public class Main {
    public static void main(String[] args) {
        //This main class is playing the role of controller in MVC Kind of design pattern
        TaxRepository taxRepository = new TaxRepositoryImpl();
        TaxCalculatorService taxCalculatorService = new TaxCalculatorServiceImpl(taxRepository);
        SalarySlipGeneratorService salarySlipGeneratorService = new SalarySlipGeneratorService(taxCalculatorService);

        if(args.length != 2){
            System.out.println("Mandatory fields name or salary is not provided.Hence cannot generate and print salary slip");
        }

        try {
            MonthlySalarySlip salarySlip = salarySlipGeneratorService.generateSalarySlip(args[0], args[1]);
            System.out.println("Monthly Payslip for: \"" + salarySlip.getName() + "\"");
            System.out.println("Gross Monthly Income:$" + salarySlip.getGrossMonthlyIncome());
            System.out.println("Monthly Income Tax:$" + salarySlip.getMonthlyIncomeTax());
            System.out.println("Net Monthly Income:$" + salarySlip.getNetMonthlyIncome());
        }  catch (TaxCalculatorException | SalarySlipGeneratorException e) {
            System.exit(1);
            System.out.println(e.getMessage());
        } catch (Exception e){
            // Catch all block so that application exists gracefully in case if there was some unknown error occurred
            // at runtime also recommended log statements for printing error stack in logs
            System.exit(1);
            System.out.println("Internal app error occurred while calculating salary slip. Plz contact support for more info");
        }

    }
}