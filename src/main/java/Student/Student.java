package Student;

import javafx.beans.property.SimpleStringProperty;

public class Student {

    private SimpleStringProperty lastName;
    private SimpleStringProperty registrationNumber;
    private SimpleStringProperty firstName;

    public Student(String firstName, String lastName, String registrationNumber) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.registrationNumber = new SimpleStringProperty(registrationNumber);
    }

    public SimpleStringProperty getFirstName() {
        return firstName;
    }

    public SimpleStringProperty getLastName() {
        return lastName;
    }

    public SimpleStringProperty getRegistrationNumber() {
        return registrationNumber;
    }


}
