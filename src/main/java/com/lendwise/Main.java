package com.lendwise;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
    try {
        System.out.println("PATH = " + getClass().getResource("/fxml/dashboard.fxml"));

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/dashboard.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("/css/styles.css").toExternalForm()
        );

        primaryStage.setTitle("LendWise - Loan Management System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error: Failed to load JavaFX application!");
    }
}


    public static void main(String[] args) {
        launch(args);
    }
}
