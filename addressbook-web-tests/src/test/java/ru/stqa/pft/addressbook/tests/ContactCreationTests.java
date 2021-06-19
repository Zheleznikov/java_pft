package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        ContactData contact = new ContactData("Unique", "New-User-Original", "+1430555555", "unique@gmail.com", "group for test contacts");
        app.getNavigationHelper().gotoAddNewUserPage();
        app.getContactHelper().createContact(contact);
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().getContactList();
        app.getSessionHelper().logout();
    }
}

