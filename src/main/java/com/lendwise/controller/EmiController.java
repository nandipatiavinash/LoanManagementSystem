package com.lendwise.controller;

import java.time.LocalDate;

import com.lendwise.model.EMI;
import com.lendwise.model.EmiStatus;
import com.lendwise.model.Loan;
import com.lendwise.repository.InMemoryLoanRepository;
import com.lendwise.service.LoanApprovalEngine;
import com.lendwise.service.LoanService;
import com.lendwise.service.PenaltyCalculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EmiController {

    @FXML private TableView<EMI> tblEmi;

    @FXML private TableColumn<EMI, Integer> colInstallment;
    @FXML private TableColumn<EMI, String> colDueDate;
    @FXML private TableColumn<EMI, Double> colPrincipal;
    @FXML private TableColumn<EMI, Double> colInterest;
    @FXML private TableColumn<EMI, Double> colTotal;
    @FXML private TableColumn<EMI, String> colStatus;

    @FXML private Label lblLoanInfo;

    private Loan currentLoan;

    private final InMemoryLoanRepository loanRepo = new InMemoryLoanRepository();
    private final LoanService loanService =
            new LoanService(loanRepo, new LoanApprovalEngine(), new PenaltyCalculator(0.02));

    private final ObservableList<EMI> emiList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
    }
    private EmiStatus status;
    private double penalty;
    private LocalDate paidDate;

    public void setStatus(EmiStatus status) { this.status = status; }
    public void setPenalty(double penalty) { this.penalty = penalty; }
    public void setPaidDate(LocalDate date) { this.paidDate = date; }

    private void setupTable() {
        colInstallment.setCellValueFactory(new PropertyValueFactory<>("installmentNumber"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colPrincipal.setCellValueFactory(new PropertyValueFactory<>("principalComponent"));
        colInterest.setCellValueFactory(new PropertyValueFactory<>("interestComponent"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblEmi.setItems(emiList);
    }

    // Called from LoanController
    public void loadLoan(Loan loan) {
        this.currentLoan = loan;

        lblLoanInfo.setText(
                "Loan ID: " + loan.getId() +
                " | Customer: " + loan.getCustomer().getName() +
                " | Type: " + loan.getLoanType()
        );

        emiList.clear();
        emiList.addAll(loan.getEmiSchedule());
    }

    public void onPayEmi() {
        EMI selected = tblEmi.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            loanService.payEmi(currentLoan, selected.getInstallmentNumber());
            emiList.clear();
            emiList.addAll(currentLoan.getEmiSchedule());

            lblLoanInfo.setText("Loan Updated: EMI Paid");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToLoans() {
        try {
            Stage stage = (Stage) tblEmi.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/fxml/loans.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
