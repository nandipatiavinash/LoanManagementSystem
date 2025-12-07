package com.lendwise.repository;

import com.lendwise.model.Customer;

import java.util.*;

public class InMemoryCustomerRepository implements CustomerRepository {

    private static final Map<String, Customer> store = new HashMap<>();

    @Override
    public void save(Customer customer) {
        store.put(customer.getId(), customer);
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
