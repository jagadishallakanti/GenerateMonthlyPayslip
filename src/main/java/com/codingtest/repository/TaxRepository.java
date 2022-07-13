package com.codingtest.repository;

import com.codingtest.exception.entity.TaxSlab;

import java.util.ArrayList;

public interface TaxRepository {
    ArrayList<TaxSlab> fetchAllSlabs();
}
