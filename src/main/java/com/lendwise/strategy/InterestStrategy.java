package com.lendwise.strategy;

public interface InterestStrategy {

    /**
     * Calculates monthly EMI.
     *
     * @param principal       Loan amount
     * @param annualRate      Annual interest rate percentage (e.g., 12 for 12%)
     * @param tenureMonths    Number of months
     * @return                EMI per month
     */
    double calculateEMI(double principal, double annualRate, int tenureMonths);
}
