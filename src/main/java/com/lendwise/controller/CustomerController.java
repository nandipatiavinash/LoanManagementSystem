package com.lendwise.controller;

import com.lendwise.model.Customer;
import com.lendwise.repository.InMemoryCustomerRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomerController {

    @FXML private TableView<Customer> tblCustomers;

    @FXML private TableColumn<Customer, String> colName;
    @FXML private TableColumn<Customer, Integer> colAge;
    @FXML private TableColumn<Customer, Double> colIncome;
    @FXML private TableColumn<Customer, Integer> colCreditScore;
    @FXML private TableColumn<Customer, String> colContact;
    @FXML private TableColumn<Customer, String> colEmail;

    private final InMemoryCustomerRepository customerRepo = new InMemoryCustomerRepository();
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadCustomers();
    }

    private void setupTable() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("monthlyIncome"));
        colCreditScore.setCellValueFactory(new PropertyValueFactory<>("creditScore"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblCustomers.setItems(customerList);
    }

    private void loadCustomers() {
        customerList.clear();
        customerList.addAll(customerRepo.findAll());
    }

    public void onAddCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_customer.fxml"));
            Pane pane = loader.load();

            Stage popup = new Stage();
            popup.setScene(new Scene(pane));
            popup.setTitle("Add Customer");
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait();

            // Reload table after adding customer
            loadCustomers();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToDashboard() {
        try {
            Stage stage = (Stage) tblCustomers.getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
