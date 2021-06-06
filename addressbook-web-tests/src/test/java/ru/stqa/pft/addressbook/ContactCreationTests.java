package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() throws Exception {
        app.gotoAddNewUserPage();
        app.fillContactForm(new ContactData("Unique", "New-User-Original", "+1430555555", "unique@gmail.com"));
        app.submitContactCreation();
        app.gotoHomePage();
        app.logout();
    }
}

