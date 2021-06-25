package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().gotoAddNewUserPage();

        if (!app.getContactHelper().isThereAContact()) {
            ContactData contact = new ContactData("contact for deleting", "New-User-Original", "+1430555555", "unique@gmail.com", "group for test contacts");
            app.goTo().gotoAddNewUserPage();
            app.getContactHelper().createContact(contact);
            app.goTo().gotoHomePage();
        }
    }

    @Test
    public void testContactDeletion() {

        List<ContactData> before = app.getContactHelper().getContactList();
        int index = before.size() - 1;

        app.getContactHelper().selectContact(index);
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().acceptAlert();
        app.goTo().gotoHomePage();

        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(before.size() - 1, after.size());
        before.remove(index);
        Assert.assertEquals(before, after);
    }
}
