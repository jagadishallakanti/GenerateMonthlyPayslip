package com.codingtest.unittests.tax;

import com.codingtest.exception.TaxCalculatorException;
import com.codingtest.exception.entity.TaxSlab;
import com.codingtest.repository.TaxRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorServiceTest {

    TaxRepository taxRepositoryMock = Mockito.mock(TaxRepository.class);
    TaxCalculatorService taxCalculatorService = new TaxCalculatorServiceImpl(taxRepositoryMock);

    @Test
    void whenCalculateTaxWithValidInput_thenReturn_CalculatedTax() throws TaxCalculatorException {
        ArrayList<TaxSlab> taxSlabs = new ArrayList<>();
        taxSlabs.add(new TaxSlab(new BigDecimal(0), new BigDecimal(20000), new BigDecimal(0)));
        taxSlabs.add(new TaxSlab(new BigDecimal(20001), new BigDecimal(40000), new BigDecimal(0.1)));
        taxSlabs.add(new TaxSlab(new BigDecimal(40001), new BigDecimal(80000), new BigDecimal(0.2)));
        taxSlabs.add(new TaxSlab(new BigDecimal(80001), new BigDecimal(180000), new BigDecimal(0.3)));
        taxSlabs.add(new TaxSlab(new BigDecimal(180001), new BigDecimal(Integer.MAX_VALUE), new BigDecimal(0.4)));

        Mockito.when(taxRepositoryMock.fetchAllSlabs()).thenReturn(taxSlabs);
        BigDecimal taxAmount = taxCalculatorService.calculateMonthlyTax(new BigDecimal("60000"));

        assertEquals(taxAmount, new BigDecimal("500.00"));
    }

    @Test
    void whenCalculateTaxWithInvalidTaxSlabValue_thenThrow_Exception() throws TaxCalculatorException {
        ArrayList<TaxSlab> taxSlabs = new ArrayList<>();
        taxSlabs.add(new TaxSlab(new BigDecimal(0), null, new BigDecimal(0)));
        Mockito.when(taxRepositoryMock.fetchAllSlabs()).thenReturn(taxSlabs);

        TaxCalculatorException taxCalculatorException = assertThrows(TaxCalculatorException.class, () -> {
            taxCalculatorService.calculateMonthlyTax(new BigDecimal("60000"));
        });

        String expectedMessage = "Tax slab entry cannot contain null values inside it";
        String actualMessage = taxCalculatorException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}