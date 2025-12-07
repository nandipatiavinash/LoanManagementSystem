package com.lendwise.model;

import com.lendwise.strategy.InterestStrategy;

import java.time.LocalDate;

public class CarLoan extends Loan {

    public CarLoan(Customer customer,
                   double principal,
                   double annualInterestRate,
                   int tenureMonths,
                   InterestStrategy strategy) {
        super(customer, principal, annualInterestRate, tenureMonths, strategy);
        this.loanType = LoanType.CAR;
    }

    @Override
    public double calculateMonthlyEMI() {
        return interestStrategy.calculateEMI(principal, annualInterestRate, tenureMonths);
    }

    @Override
    public void generateEmiSchedule() {
        emiSchedule.clear();
        double emi = calculateMonthlyEMI();

        LocalDate dueDate = startDate.plusMonths(1);
        double remaining = principal;

        for (int i = 1; i <= tenureMonths; i++) {

            double interestComponent = (remaining * (annualInterestRate / 100)) / 12;
            double principalComponent = emi - interestComponent;

            if (principalComponent > remaining) {
                principalComponent = remaining;
            }

            EMI e = new EMI(i, dueDate, principalComponent, interestComponent);
            emiSchedule.add(e);

            remaining -= principalComponent;
            dueDate = dueDate.plusMonths(1);
        }
    }
}
