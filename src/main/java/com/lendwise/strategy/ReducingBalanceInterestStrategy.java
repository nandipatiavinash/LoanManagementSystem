package com.lendwise.strategy;

public class ReducingBalanceInterestStrategy implements InterestStrategy {

    @Override
    public double calculateEMI(double principal, double annualRate, int tenureMonths) {

        double monthlyRate = (annualRate / 100.0) / 12.0;

        // EMI formula = [P * r * (1+r)^n] / [(1+r)^n - 1]
        double factor = Math.pow(1 + monthlyRate, tenureMonths);

        return principal * monthlyRate * factor / (factor - 1);
    }
}
