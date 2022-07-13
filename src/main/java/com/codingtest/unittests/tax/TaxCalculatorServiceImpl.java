package com.codingtest.unittests.tax;

import com.codingtest.exception.TaxCalculatorException;
import com.codingtest.exception.entity.TaxSlab;
import com.codingtest.repository.TaxRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
public class TaxCalculatorServiceImpl implements TaxCalculatorService {
    TaxRepository taxRepository;

    @Override
    public BigDecimal calculateMonthlyTax(BigDecimal annualSalary) throws TaxCalculatorException {
        BigDecimal additionalTaxAmt = BigDecimal.ZERO;

        for (TaxSlab taxSlab : taxRepository.fetchAllSlabs()) {
            if(taxSlab.getMinLimit() ==null || taxSlab.getMaxLimit() == null || taxSlab.getTaxRate() == null){
                throw new TaxCalculatorException("Tax slab entry cannot contain null values inside it");
            }

            if (annualSalary.compareTo(taxSlab.getMaxLimit()) > 0) {
                BigDecimal standardTaxRange = taxSlab.getMaxLimit().subtract(taxSlab.getMinLimit()).add(new BigDecimal(1));
                BigDecimal taxValForRange = standardTaxRange.multiply(taxSlab.getTaxRate());
                additionalTaxAmt = additionalTaxAmt.add(taxValForRange);
            } else {
                BigDecimal currentTaxRange = annualSalary.subtract(taxSlab.getMinLimit()).add(new BigDecimal(1));
                BigDecimal taxForCurrentTaxRange = currentTaxRange.multiply(taxSlab.getTaxRate());
                BigDecimal totalTaxForMonth = taxForCurrentTaxRange.add(additionalTaxAmt).divide(new BigDecimal(12),2, RoundingMode.HALF_UP);

                return totalTaxForMonth;
            }
        }

        return null;
    }
}
