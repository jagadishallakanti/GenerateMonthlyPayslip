package com.codingtest.repository;

import com.codingtest.exception.entity.TaxSlab;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TaxRepositoryImpl implements TaxRepository{

    // Generally in real time below method interact will be either
    // with cache service like redis or Database
    @Override
    public ArrayList<TaxSlab> fetchAllSlabs() {
        ArrayList<TaxSlab> taxSlabs = new ArrayList<>();

        taxSlabs.add(new TaxSlab(
                new BigDecimal(0),
                new BigDecimal(20000),
                new BigDecimal(0)));
        taxSlabs.add(new TaxSlab(
                new BigDecimal(20001),
                new BigDecimal(40000),
                new BigDecimal(0.1)));
        taxSlabs.add(new TaxSlab(
                new BigDecimal(40001),
                new BigDecimal(80000),
                new BigDecimal(0.2)));
        taxSlabs.add(new TaxSlab(
                new BigDecimal(80001),
                new BigDecimal(180000),
                new BigDecimal(0.3)));
        taxSlabs.add(new TaxSlab(
                new BigDecimal(180001),
                new BigDecimal(Integer.MAX_VALUE),
                new BigDecimal(0.4)));


        return taxSlabs;
    }
}
