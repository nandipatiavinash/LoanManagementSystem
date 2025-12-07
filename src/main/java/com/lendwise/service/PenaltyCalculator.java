package com.lendwise.service;

import com.lendwise.model.EMI;
import com.lendwise.model.EmiStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PenaltyCalculator {

    private final double dailyPenaltyRate; // e.g., 0.02 means 0.02% per day

    /**
     * @param dailyPenaltyRate percentage per day (e.g., 0.02 = 0.02% per day)
     */
    public PenaltyCalculator(double dailyPenaltyRate) {
        this.dailyPenaltyRate = dailyPenaltyRate;
    }

    /**
     * Calculates late penalty for a given EMI.
     * Returns 0 if EMI is NOT late.
     */
    public double calculatePenalty(EMI emi) {

        // Already paid → No penalty
        if (emi.getStatus() == EmiStatus.PAID || emi.getStatus() == EmiStatus.LATE) {
            return 0.0;
        }

        LocalDate today = LocalDate.now();

        // If due date is in the future → Not late
        if (!emi.getDueDate().isBefore(today)) {
            return 0.0;
        }

        long daysLate = ChronoUnit.DAYS.between(emi.getDueDate(), today);

        if (daysLate <= 0) {
            return 0.0;
        }

        // Penalty = EMI * daily_rate% * daysLate
        return emi.getTotalAmount() * (dailyPenaltyRate / 100.0) * daysLate;
    }
}
