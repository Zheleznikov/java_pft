package ru.stqa.pft.addressbook;

public class ContactData {
    private final String name;
    private final String lastName;
    private final String mobilePhone;
    private final String email;

    public ContactData(String name, String lastName, String mobilePhone, String email) {
        this.name = name;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmail() {
        return email;
    }
}
