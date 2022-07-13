package com.codingtest.repository;

import com.codingtest.entity.TaxSlab;

import java.util.ArrayList;

public interface TaxRepository {
    ArrayList<TaxSlab> fetchAllSlabs();
}
