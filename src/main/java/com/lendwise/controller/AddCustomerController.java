package com.lendwise.controller;

import com.lendwise.model.Customer;
import com.lendwise.repository.InMemoryCustomerRepository;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCustomerController {

    @FXML private TextField txtName;
    @FXML private TextField txtAge;
    @FXML private TextField txtIncome;
    @FXML private TextField txtCredit;
    @FXML private TextField txtContact;
    @FXML private TextField txtEmail;

    private final InMemoryCustomerRepository customerRepo = new InMemoryCustomerRepository();

    public void onSaveCustomer() {
        try {
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            double income = Double.parseDouble(txtIncome.getText());
            int credit = Integer.parseInt(txtCredit.getText());
            String contact = txtContact.getText();
            String email = txtEmail.getText();
            Customer c = new Customer(name, age, email, contact, income, credit);
            customerRepo.save(c);
            // close popup
            Stage stage = (Stage) txtName.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Invalid input!");
        }
    }
}
