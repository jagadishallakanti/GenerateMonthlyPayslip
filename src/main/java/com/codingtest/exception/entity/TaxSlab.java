package com.codingtest.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TaxSlab {
    private BigDecimal minLimit;
    private BigDecimal maxLimit;
    private BigDecimal taxRate;
}
