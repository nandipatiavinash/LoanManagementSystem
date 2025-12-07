package com.lendwise.controller;

import com.lendwise.model.Loan;
import com.lendwise.repository.InMemoryLoanRepository;
import com.lendwise.model.Loan;
import javafx.scene.input.MouseEvent;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoanController {

    @FXML private TableView<Loan> tblLoans;

    @FXML private TableColumn<Loan, String> colLoanId;
    @FXML private TableColumn<Loan, String> colCustomer;
    @FXML private TableColumn<Loan, String> colType;
    @FXML private TableColumn<Loan, Double> colPrincipal;
    @FXML private TableColumn<Loan, Integer> colTenure;
    @FXML private TableColumn<Loan, String> colStatus;

    private final InMemoryLoanRepository loanRepo = new InMemoryLoanRepository();
    private final ObservableList<Loan> loanList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadLoans();
        tblLoans.setOnMouseClicked(this::onLoanClicked);
    }
    private void onLoanClicked(MouseEvent event) {
    if (event.getClickCount() == 2) { // double click to open EMI schedule
        Loan selected = tblLoans.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/emi_schedule.fxml"));
            Pane pane = loader.load();

            EmiController controller = loader.getController();
            controller.loadLoan(selected);

            Stage stage = (Stage) tblLoans.getScene().getWindow();
            stage.setScene(new Scene(pane));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


    private void setupTable() {
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colPrincipal.setCellValueFactory(new PropertyValueFactory<>("principal"));
        colTenure.setCellValueFactory(new PropertyValueFactory<>("tenureMonths"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusText"));
        colType.setCellValueFactory(new PropertyValueFactory<>("loanTypeText"));

        tblLoans.setItems(loanList);
    }

    private void loadLoans() {
        loanList.clear();
        loanList.addAll(loanRepo.findAll());
    }

    public void onCreateLoan() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create_loan.fxml"));
            Pane pane = loader.load();

            Stage popup = new Stage();
            popup.setScene(new Scene(pane));
            popup.setTitle("Create Loan");
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait();

            loadLoans();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToDashboard() {
        try {
            Stage stage = (Stage) tblLoans.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
