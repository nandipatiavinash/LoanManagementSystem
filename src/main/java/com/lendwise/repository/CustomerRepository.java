package com.lendwise.repository;

import com.lendwise.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(String id);
}
