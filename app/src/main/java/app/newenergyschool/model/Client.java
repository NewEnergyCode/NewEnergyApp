package app.newenergyschool.model;

import android.widget.EditText;

public class Client {

    private String telephoneNumber;
    private String firstName;
    private String secondName;
    private String childName;
    private String childAge;

    public Client(String telephoneNumber, String firstName, String secondName, String childName, String childAge) {
        this.telephoneNumber = telephoneNumber;
        this.firstName = firstName;
        this.secondName = secondName;
        this.childName = childName;
        this.childAge = childAge;
    }
    public Client() {
    }

    public Client(EditText telephoneNumber, EditText firstName, EditText secondName, EditText childName, EditText childAge) {
        this.telephoneNumber = telephoneNumber.getText().toString();
        this.firstName = firstName.getText().toString();
        this.secondName = secondName.getText().toString();
        this.childName = childName.getText().toString();
        this.childAge = childAge.getText().toString();
    }

    @Override
    public String toString() {
        return "Client{" +
                "telephoneNumber='" + telephoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", childName='" + childName + '\'' +
                ", childAge='" + childAge + '\'' +
                '}';
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }
}
