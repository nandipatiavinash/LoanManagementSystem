package com.lendwise.model;

import java.io.Serializable;
import java.time.LocalDate;

public class EMI implements Serializable {

    private int installmentNumber;        // EMI number (1..N)
    private LocalDate dueDate;            // Scheduled due date

    private double principalComponent;    // Portion of principal paid
    private double interestComponent;     // Portion of interest paid
    private double totalAmount;           // principal + interest + penalties

    private EmiStatus status;             // PENDING / PAID / LATE
    private double penaltyAmount;         // Total accumulated penalty

    private LocalDate paidDate;           // Payment date

    public EMI(int installmentNumber,
               LocalDate dueDate,
               double principalComponent,
               double interestComponent) {

        this.installmentNumber = installmentNumber;
        this.dueDate = dueDate;
        this.principalComponent = principalComponent;
        this.interestComponent = interestComponent;

        this.totalAmount = principalComponent + interestComponent;
        this.status = EmiStatus.PENDING;
        this.penaltyAmount = 0.0;
        this.paidDate = null;
    }

    // ================== Getters ==================

    public int getInstallmentNumber() { return installmentNumber; }

    public LocalDate getDueDate() { return dueDate; }

    public double getPrincipalComponent() { return principalComponent; }

    public double getInterestComponent() { return interestComponent; }

    public double getTotalAmount() { return totalAmount; }

    public EmiStatus getStatus() { return status; }

    public double getPenaltyAmount() { return penaltyAmount; }

    public LocalDate getPaidDate() { return paidDate; }

    // ================== Setters ==================

    public void setStatus(EmiStatus status) { this.status = status; }

    public void setPaidDate(LocalDate date) { this.paidDate = date; }

    public void addPenalty(double penalty) {
        this.penaltyAmount += penalty;
        this.totalAmount += penalty;
    }

    // For JavaFX TableView (String column)
    public String getStatusText() {
        return status.name();
    }
}
