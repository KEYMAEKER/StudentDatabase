module com.example.studentdatabase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.studentdatabase to javafx.fxml;
    exports com.example.studentdatabase;
}