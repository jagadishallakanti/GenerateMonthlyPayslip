package com.codingtest.e2e;

import com.codingtest.exception.SalarySlipGeneratorException;
import com.codingtest.exception.TaxCalculatorException;
import com.codingtest.exception.entity.MonthlySalarySlip;
import com.codingtest.repository.TaxRepository;
import com.codingtest.repository.TaxRepositoryImpl;
import com.codingtest.unittests.SalarySlipGeneratorService;
import com.codingtest.unittests.tax.TaxCalculatorService;
import com.codingtest.unittests.tax.TaxCalculatorServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SalarySlipGeneratorServiceTest {
    TaxRepository taxRepository = new TaxRepositoryImpl();
    TaxCalculatorService taxCalculatorService = new TaxCalculatorServiceImpl(taxRepository);
    SalarySlipGeneratorService salarySlipGeneratorService = new SalarySlipGeneratorService(taxCalculatorService);


    @Test
    void GenerateSalarySlipForZeroTaxBracket() throws TaxCalculatorException, SalarySlipGeneratorException {
        MonthlySalarySlip monthlySalarySlip = salarySlipGeneratorService.generateSalarySlip("Jagadish", "18000");

        assertEquals(monthlySalarySlip.getGrossMonthlyIncome(), new BigDecimal("1500.00"));
        assertEquals(monthlySalarySlip.getMonthlyIncomeTax(), new BigDecimal("0.00"));
        assertEquals(monthlySalarySlip.getNetMonthlyIncome(), new BigDecimal("1500.00"));
    }

    @Test
    void GenerateSalarySlipForHighestTaxBracket() throws TaxCalculatorException, SalarySlipGeneratorException {
        MonthlySalarySlip monthlySalarySlip = salarySlipGeneratorService.generateSalarySlip("Jagadish", "185000");

        assertEquals(monthlySalarySlip.getGrossMonthlyIncome(), new BigDecimal("15416.67"));
        assertEquals(monthlySalarySlip.getMonthlyIncomeTax(), new BigDecimal("3500.00"));
        assertEquals(monthlySalarySlip.getNetMonthlyIncome(), new BigDecimal("11916.67"));
    }

    @Test
    void ShouldThrowExceptionWhenInvalidInputIsProvided() throws TaxCalculatorException, SalarySlipGeneratorException {
        SalarySlipGeneratorException exception = assertThrows(SalarySlipGeneratorException.class, () -> {
            salarySlipGeneratorService.generateSalarySlip("Dummy_user", "InvalidSalaryAmt");
        });

        String expectedMessage = "Invalid value provided for salary. Plz provide only numeric value for salary";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}