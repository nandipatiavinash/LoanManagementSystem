package com.lendwise.controller;

import com.lendwise.repository.InMemoryCustomerRepository;
import com.lendwise.repository.InMemoryLoanRepository;
import com.lendwise.model.Loan;
import com.lendwise.model.LoanStatus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class DashboardController {

    // ===== Dashboard labels from FXML =====
    @FXML
    private Label lblTotalCustomers;

    @FXML
    private Label lblActiveLoans;

    @FXML
    private Label lblOverdueEmis;

    // ===== Repositories (temporary in-memory storage) =====
    private final InMemoryCustomerRepository customerRepo = new InMemoryCustomerRepository();
    private final InMemoryLoanRepository loanRepo = new InMemoryLoanRepository();

    // Called automatically when FXML loads
    @FXML
    public void initialize() {
        refreshDashboard();
    }

    // ===== Refresh dashboard values =====
    private void refreshDashboard() {
        // Total Customers
        lblTotalCustomers.setText(String.valueOf(customerRepo.findAll().size()));

        // Active Loans
        long activeLoans = loanRepo.findAll().stream()
                .filter(loan -> loan.getStatus() == LoanStatus.ACTIVE)
                .count();
        lblActiveLoans.setText(String.valueOf(activeLoans));

        // Overdue EMIs
        AtomicInteger overdueCount = new AtomicInteger();
        loanRepo.findAll().forEach(loan ->
                loan.getEmiSchedule().forEach(emi -> {
                    if (emi.getDueDate().isBefore(java.time.LocalDate.now())
                            && emi.getStatus().name().equals("PENDING")) {
                        overdueCount.getAndIncrement();
                    }
                })
        );

        lblOverdueEmis.setText(String.valueOf(overdueCount.get()));
    }

    // ===== Navigation buttons =====
    public void onCustomersClicked() {
        switchScene("/fxml/customers.fxml");
    }

    public void onLoansClicked() {
        switchScene("/fxml/loans.fxml");
    }

    // ===== Helper method to change scenes =====
    private void switchScene(String fxmlFile) {
        try {
            Stage stage = (Stage) lblTotalCustomers.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Unable to load scene " + fxmlFile);
        }
    }
}
