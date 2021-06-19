package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        ContactData contactForCreation = new ContactData("contact for creating", "New-User-Original", "+1430555555", "unique@gmail.com", "group for test contacts");
        ContactData contactForModification = new ContactData("Modified contact", "New-User-Original", "+1430555555", "unique@gmail.com", "group for test contacts");
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoAddNewUserPage();
            app.getContactHelper().createContact(contactForCreation);
            app.getNavigationHelper().gotoHomePage();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().modificateSelectedContacts();
        app.getContactHelper().fillContactForm(contactForModification, false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHomePage();
        app.getSessionHelper().logout();
    }
}
