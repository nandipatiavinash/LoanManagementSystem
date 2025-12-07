package com.lendwise.service;

import com.lendwise.model.Customer;
import com.lendwise.model.Loan;

public class LoanApprovalEngine {

    private String lastRejectionReason = "";

    public String getLastRejectionReason() {
        return lastRejectionReason;
    }

    // MAIN APPROVAL METHOD
    public boolean approveLoan(Loan loan) {

        Customer c = loan.getCustomer();

        // RULE 1: Minimum credit score
        if (c.getCreditScore() < 600) {
            lastRejectionReason = "Low credit score";
            return false;
        }

        // RULE 2: Principal allowed based on income
        double maxAllowed = c.getMonthlyIncome() * 60;
        if (loan.getPrincipal() > maxAllowed) {
            lastRejectionReason = "Loan amount too high for income";
            return false;
        }

        // RULE 3: Tenure limit
        if (loan.getTenureMonths() > 360) {
            lastRejectionReason = "Loan tenure too long";
            return false;
        }

        // RULE 4: Age rule
        if (c.getAge() < 18 || c.getAge() > 65) {
            lastRejectionReason = "Invalid customer age";
            return false;
        }

        // RULE 5: Principal positive
        if (loan.getPrincipal() <= 0) {
            lastRejectionReason = "Invalid principal amount";
            return false;
        }

        lastRejectionReason = "";
        return true;
    }
}
