package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().gotoAddNewUserPage();
        app.getContactHelper().fillContactForm(new ContactData("Unique", "New-User-Original", "+1430555555", "unique@gmail.com", "group for test contacts"), true);
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().gotoHomePage();
        app.getSessionHelper().logout();
    }
}

