package com.lendwise.repository;

import com.lendwise.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void save(Loan loan);
    List<Loan> findAll();
    Optional<Loan> findById(String id);
}
