package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String name;
    private final String lastName;
    private final String mobilePhone;
    private final String email;
    private final String group;

    public ContactData(String name, String lastName, String mobilePhone, String email, String group) {
        this.name = name;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.group = group;
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

    public String getGroup() {return group;}
}
