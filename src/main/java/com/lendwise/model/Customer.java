package com.lendwise.model;

import java.io.Serializable;
import java.util.UUID;

public class Customer implements Serializable {

    private final String id;
    private String name;
    private int age;
    private String email;
    private String phone;
    private double monthlyIncome;
    private int creditScore;

    public Customer(String name, int age, String email, String phone,
                    double monthlyIncome, int creditScore) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.monthlyIncome = monthlyIncome;
        this.creditScore = creditScore;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public double getMonthlyIncome() { return monthlyIncome; }
    public int getCreditScore() { return creditScore; }

    @Override
    public String toString() {
        return name + " | Income: " + monthlyIncome;
    }
}
