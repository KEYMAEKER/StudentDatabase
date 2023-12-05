package com.example.studentdatabase;

import Student.StudentTable;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloApplication extends Application {
    Connection con;
    PreparedStatement query;
    StudentTable table = new StudentTable();

    @Override
    public void start(Stage stage) {


        BorderPane layout = new BorderPane();
        GridPane gridPane = new GridPane();

        Label firstName = new Label("Enter First Name: ");
        Label lastName = new Label("Enter Last Name: ");
        Label registrationNumber = new Label("Enter Registration Number: ");
        Label errorLabel = new Label("");

        Button saveButton = new Button("SAVE");
        Button viewStudentsButton = new Button("View Registered Students");
        saveButton.setPrefSize(70, 50);
        viewStudentsButton.setPrefSize(70, 50);

        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();
        TextField registrationNumberTextField = new TextField();

        gridPane.add(firstName, 0, 0);
        gridPane.add(lastName, 0, 1);
        gridPane.add(registrationNumber, 0, 2);
        gridPane.add(errorLabel, 1, 4);

        gridPane.add(firstNameTextField, 1, 0);
        gridPane.add(lastNameTextField, 1, 1);
        gridPane.add(registrationNumberTextField, 1, 2);
        gridPane.add(saveButton, 1, 3);
        gridPane.add(viewStudentsButton, 0, 3);

        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        StackPane stackPane = new StackPane();
        final Popup popup = new Popup();
        Label successLabel = new Label("Items added to the database");
        stackPane.getChildren().add(successLabel);
        popup.getContent().add(stackPane);

        popup.setAutoFix(true);
        popup.setAutoHide(true);

        layout.setTop(gridPane);

        saveButton.setOnAction(actionEvent -> {
            String firstNameInput = firstNameTextField.getText();
            String lastNameInput = lastNameTextField.getText();
            String registrationNumberInput = registrationNumberTextField.getText();

            if (!firstNameInput.isEmpty() && !lastNameInput.isEmpty() && !registrationNumberInput.isEmpty()) {

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost/log", "root", "");
                    query = con.prepareStatement("insert into students(firstName,secondName,regNo) VALUES (?,?,?)");

                    query.setString(1, firstNameInput);
                    query.setString(2, lastNameInput);
                    query.setString(3, registrationNumberInput);
                    query.executeUpdate();

                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }

                errorLabel.setText("");

                firstNameTextField.clear();
                lastNameTextField.clear();
                registrationNumberTextField.clear();

                popup.show(stage);

            } else {

                errorLabel.setText("Error: One or more fields are empty");

            }
        });

        viewStudentsButton.setOnAction(actionEvent -> {
            stage.setTitle("REGISTERED STUDENTS");
            stage.setWidth(400);
            stage.setHeight(500);
            Scene tableView = new Scene(table.createTable());
            stage.setScene(tableView);

        });

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(HelloApplication.class);
    }
}