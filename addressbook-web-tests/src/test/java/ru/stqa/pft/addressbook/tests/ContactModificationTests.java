package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

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
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact();
        app.getContactHelper().modificateSelectedContacts();
        app.getContactHelper().fillContactForm(contactForModification, false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        app.getSessionHelper().logout();

        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
