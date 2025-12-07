package com.lendwise.controller;

import com.lendwise.model.*;
import com.lendwise.repository.InMemoryCustomerRepository;
import com.lendwise.repository.InMemoryLoanRepository;
import com.lendwise.service.LoanApprovalEngine;
import com.lendwise.service.LoanService;
import com.lendwise.service.PenaltyCalculator;
import com.lendwise.strategy.FlatInterestStrategy;
import com.lendwise.strategy.InterestStrategy;
import com.lendwise.strategy.ReducingBalanceInterestStrategy;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateLoanController {

    @FXML private ComboBox<Customer> cmbCustomer;
    @FXML private ComboBox<String> cmbLoanType;
    @FXML private ComboBox<String> cmbInterestType;

    @FXML private TextField txtPrincipal;
    @FXML private TextField txtTenure;
    @FXML private Label lblEmi;

    private final InMemoryCustomerRepository customerRepo = new InMemoryCustomerRepository();
    private final InMemoryLoanRepository loanRepo = new InMemoryLoanRepository();
    private final LoanService loanService =
            new LoanService(loanRepo, new LoanApprovalEngine(), new PenaltyCalculator(0.02));

    private InterestStrategy strategy;

    @FXML
    public void initialize() {

        // Load Customers
        cmbCustomer.getItems().addAll(customerRepo.findAll());

        // Loan types
        cmbLoanType.getItems().addAll("HOME", "CAR", "PERSONAL");

        // Interest strategies
        cmbInterestType.getItems().addAll("Flat Interest", "Reducing Balance EMI");

        cmbInterestType.setOnAction(e -> {
            if (cmbInterestType.getValue().equals("Flat Interest")) {
                strategy = new FlatInterestStrategy();
            } else {
                strategy = new ReducingBalanceInterestStrategy();
            }
        });
    }

    public void onCalculateEmi() {
        try {
            double principal = Double.parseDouble(txtPrincipal.getText());
            int tenure = Integer.parseInt(txtTenure.getText());

            if (strategy == null) {
                lblEmi.setText("Select Interest Type");
                return;
            }

            double emi = strategy.calculateEMI(principal, 12, tenure); // default 12% interest
            lblEmi.setText(String.format("%.2f", emi));

        } catch (Exception e) {
            lblEmi.setText("Invalid input");
        }
    }

    public void onSaveLoan() {
        try {
            Customer customer = cmbCustomer.getValue();
            String type = cmbLoanType.getValue();
            double principal = Double.parseDouble(txtPrincipal.getText());
            int tenure = Integer.parseInt(txtTenure.getText());

            Loan loan;

            switch (type) {
                case "HOME":
                    loan = new HomeLoan(customer, principal, 12, tenure, strategy);
                    break;

                case "CAR":
                    loan = new CarLoan(customer, principal, 10, tenure, strategy);
                    break;

                default:
                    loan = new PersonalLoan(customer, principal, 14, tenure, strategy);
            }

            loanService.createLoan(loan);

            Stage stage = (Stage) txtPrincipal.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
