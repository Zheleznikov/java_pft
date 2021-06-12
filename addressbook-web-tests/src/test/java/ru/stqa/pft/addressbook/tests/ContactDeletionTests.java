package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoAddNewUserPage();
            app.getContactHelper().createContact(new ContactData("contact for deleting", "New-User-Original", "+1430555555", "unique@gmail.com", "group for test contacts"));
            app.getNavigationHelper().gotoHomePage();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().acceptAlert();
        app.getSessionHelper().logout();
    }
}
