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
            app.goTo().gotoAddNewUserPage();
            app.getContactHelper().createContact(contact);
            app.goTo().gotoHomePage();
        }

        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().acceptAlert();
        app.goTo().gotoHomePage();

        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(before.size() - 1, after.size());
        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
