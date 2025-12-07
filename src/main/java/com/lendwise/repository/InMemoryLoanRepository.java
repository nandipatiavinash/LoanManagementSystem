package com.lendwise.repository;

import com.lendwise.model.Loan;

import java.util.*;

public class InMemoryLoanRepository implements LoanRepository {

    // Static store ensures data stays consistent across controllers
    private static final Map<String, Loan> store = new HashMap<>();

    @Override
    public void save(Loan loan) {
        store.put(loan.getId(), loan);
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Loan> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
