package Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.*;

public class StudentTable {
    private final TableView<Student> tableView = new TableView<>();
    private final ObservableList<Student> studentArrayList = FXCollections.observableArrayList();

    public Parent createTable() {

        final Label label = new Label("Student List");
        label.setFont(new Font("Arial", 20));

        tableView.setEditable(true);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/log", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                String nameOne = resultSet.getString("firstName");
                String nameTwo = resultSet.getString("secondName");
                String regNo = resultSet.getString("regNo");

                studentArrayList.add(new Student(nameOne, nameTwo, regNo));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        System.out.println(studentArrayList.size());

        TableColumn<Student, String> firstName = new TableColumn<>("First Name");
        TableColumn<Student, String> lastName = new TableColumn<>("Last Name");
        TableColumn<Student, String> registrationNumber = new TableColumn<>("Registration Number");

        firstName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
        lastName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
        registrationNumber.setCellValueFactory(cellData -> cellData.getValue().getRegistrationNumber());

        firstName.setCellFactory(TextFieldTableCell.forTableColumn());
        lastName.setCellFactory(TextFieldTableCell.forTableColumn());
        registrationNumber.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.getColumns().addAll(firstName, lastName, registrationNumber);
        tableView.setItems(studentArrayList);

        final VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 0, 0, 10));
        vBox.getChildren().addAll(label, tableView);

        return vBox;

    }

}
