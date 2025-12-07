package com.lendwise.model;

import com.lendwise.strategy.InterestStrategy;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Loan implements Serializable {

    protected final String id;
    protected LoanType loanType;
    protected Customer customer;
    protected double principal;
    protected double annualInterestRate;   
    protected int tenureMonths;
    protected LoanStatus status;
    protected LocalDate startDate;
    protected List<EMI> emiSchedule;

    protected transient InterestStrategy interestStrategy;

    // NEW: rejection reason
    protected String rejectionReason;  

    public Loan(Customer customer,
                double principal,
                double annualInterestRate,
                int tenureMonths,
                InterestStrategy interestStrategy) {

        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.principal = principal;
        this.annualInterestRate = annualInterestRate;
        this.tenureMonths = tenureMonths;
        this.status = LoanStatus.PENDING_APPROVAL;
        this.startDate = LocalDate.now();
        this.emiSchedule = new ArrayList<>();
        this.interestStrategy = interestStrategy;
    }

    // ------------ Abstract Methods ---------------
    public abstract double calculateMonthlyEMI();
    public abstract void generateEmiSchedule();

    // ------------ Loan Status Handling ---------------
    public void approve() {
        this.status = LoanStatus.APPROVED;
    }

    public void activate() {
        this.status = LoanStatus.ACTIVE;
    }

    public void close() {
        this.status = LoanStatus.CLOSED;
    }

    // NEW: Reject Method
    public void reject(String reason) {
        this.status = LoanStatus.REJECTED;
        this.rejectionReason = reason;
    }

    // NEW Getter for UI/Logs
    public String getRejectionReason() {
        return rejectionReason;
    }

    // ------------ Basic Getters ---------------
    public String getId() {
        return id;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getPrincipal() {
        return principal;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public List<EMI> getEmiSchedule() {
        return emiSchedule;
    }

    // ------------ JavaFX TableView Helper Methods ---------------

    public String getCustomerName() {
        return customer != null ? customer.getName() : "";
    }

    public String getLoanTypeText() {
        return loanType != null ? loanType.name() : "";
    }

    public String getStatusText() {
        return status != null ? status.name() : "";
    }
}
