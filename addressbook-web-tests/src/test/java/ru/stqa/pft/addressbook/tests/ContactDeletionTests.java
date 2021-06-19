package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        ContactData contact = new ContactData("contact for deleting", "New-User-Original", "+1430555555", "unique@gmail.com", "group for test contacts");
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoAddNewUserPage();
            app.getContactHelper().createContact(contact);
            app.getNavigationHelper().gotoHomePage();
        }

        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().acceptAlert();
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();

        app.getSessionHelper().logout();
        Assert.assertEquals(before.size() - 1, after.size());
        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
