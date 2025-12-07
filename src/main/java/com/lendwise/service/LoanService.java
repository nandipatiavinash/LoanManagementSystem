package com.lendwise.service;

import com.lendwise.model.*;
import com.lendwise.repository.LoanRepository;

import java.util.List;

public class LoanService {

    private final LoanRepository loanRepo;
    private final LoanApprovalEngine approvalEngine;
    private final PenaltyCalculator penaltyCalculator;

    public LoanService(LoanRepository loanRepo,
                       LoanApprovalEngine approvalEngine,
                       PenaltyCalculator penaltyCalculator) {
        this.loanRepo = loanRepo;
        this.approvalEngine = approvalEngine;
        this.penaltyCalculator = penaltyCalculator;
    }

    // ---------------------------------------------------
    // CREATE LOAN
    // ---------------------------------------------------
    public boolean createLoan(Loan loan) {
        boolean approved = approvalEngine.approveLoan(loan);

        if (!approved) {
            return false; // reject loan
        }

        loan.approve();
        loan.activate();
        loan.generateEmiSchedule();

        loanRepo.save(loan);
        return true;
    }

    // ---------------------------------------------------
    // PAY EMI
    // ---------------------------------------------------
    public void payEmi(Loan loan, int installmentNumber) {

        if (loan == null) return;

        List<EMI> schedule = loan.getEmiSchedule();
        if (installmentNumber <= 0 || installmentNumber > schedule.size()) return;

        EMI emi = schedule.get(installmentNumber - 1);

        // Already paid?
        if (emi.getStatus() == EmiStatus.PAID) {
            return;
        }

        // Apply penalty if overdue
        if (emi.getDueDate().isBefore(java.time.LocalDate.now())) {
            double penalty = penaltyCalculator.calculatePenalty(emi);
            emi.addPenalty(penalty);
        }

        // Mark as paid
        emi.setStatus(EmiStatus.PAID);
        emi.setPaidDate(java.time.LocalDate.now());

        // Check if loan is fully paid
        boolean allPaid = schedule.stream()
                .allMatch(e -> e.getStatus() == EmiStatus.PAID);

        if (allPaid) {
            loan.close();
        }

        // Save to repository
        loanRepo.save(loan);
    }

    // ---------------------------------------------------
    // GET ALL LOANS
    // ---------------------------------------------------
    public List<Loan> getAllLoans() {
        return loanRepo.findAll();
    }
}
