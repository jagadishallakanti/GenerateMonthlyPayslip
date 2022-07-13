package com.codingtest.unittests;

import com.codingtest.exception.SalarySlipGeneratorException;
import com.codingtest.exception.TaxCalculatorException;
import com.codingtest.exception.entity.MonthlySalarySlip;
import com.codingtest.unittests.tax.TaxCalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SalarySlipGeneratorServiceTest {
    TaxCalculatorService taxCalculatorService = Mockito.mock(TaxCalculatorService.class);
    SalarySlipGeneratorService salarySlipGeneratorService = new SalarySlipGeneratorService(taxCalculatorService);

    @Test
    void whenGenerateSalarySlip_calledWithValidInput_returnMonthlySalarySlip() throws TaxCalculatorException, SalarySlipGeneratorException {
        String mockUserName = "Dummy_user";
        Mockito.when(taxCalculatorService.calculateMonthlyTax(Mockito.any())).thenReturn(new BigDecimal(500));
        MonthlySalarySlip monthlySalarySlip = salarySlipGeneratorService.generateSalarySlip(mockUserName, "60000");

        assertTrue( monthlySalarySlip.getName().equals(mockUserName));
        assertEquals(monthlySalarySlip.getGrossMonthlyIncome(), new BigDecimal("5000.00"));
        assertEquals(monthlySalarySlip.getMonthlyIncomeTax(), new BigDecimal(500));
        assertEquals(monthlySalarySlip.getNetMonthlyIncome(), new BigDecimal("4500.00"));
    }

    @Test
    void whenGenerateSalarySlip_CalledWithInvalidInput_ThrowsException() throws TaxCalculatorException, SalarySlipGeneratorException {
        SalarySlipGeneratorException exception = assertThrows(SalarySlipGeneratorException.class, () -> {
            salarySlipGeneratorService.generateSalarySlip("Dummy_user", "InvalidSalaryAmt");
        });

        String expectedMessage = "Invalid value provided for salary. Plz provide only numeric value for salary";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenGenerateSalarySlip_CalledWithNegativeSalary_ThrowsException() throws TaxCalculatorException, SalarySlipGeneratorException {
        SalarySlipGeneratorException exception = assertThrows(SalarySlipGeneratorException.class, () -> {
            salarySlipGeneratorService.generateSalarySlip("Dummy_user", "-100");
        });

        String expectedMessage = "Annual salary cannot be negative or zero";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenGenerateSalarySlip_TaxCalculatorExceptionOccurs_RethrowThatException() throws TaxCalculatorException, SalarySlipGeneratorException {
        Mockito.when(taxCalculatorService.calculateMonthlyTax(Mockito.any())).thenThrow(new TaxCalculatorException("Unable to calculate tax"));
        TaxCalculatorException exception = assertThrows(TaxCalculatorException.class, () -> {
            salarySlipGeneratorService.generateSalarySlip("Dummy_user", "100");
        });

        String expectedMessage = "Unable to calculate tax";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}