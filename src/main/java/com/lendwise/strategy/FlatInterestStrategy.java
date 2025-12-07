package com.lendwise.strategy;

public class FlatInterestStrategy implements InterestStrategy {

    @Override
    public double calculateEMI(double principal, double annualRate, int tenureMonths) {

        // Total interest = P * R * T (simple interest)
        double totalInterest = principal * (annualRate / 100.0) * (tenureMonths / 12.0);

        double totalAmount = principal + totalInterest;

        // EMI = total amount divided equally
        return totalAmount / tenureMonths;
    }
}
