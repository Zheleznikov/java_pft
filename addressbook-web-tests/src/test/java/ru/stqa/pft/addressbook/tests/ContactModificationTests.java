package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().gotoHomePage();
        if (!app.getContactHelper().isThereAContact()) {
            ContactData contactForCreation = new ContactData("Contact for modify", "Contact for modify", "+1430555555", "unique@gmail.com", "group for test contacts");
            app.goTo().gotoAddNewUserPage();
            app.getContactHelper().createContact(contactForCreation);
            app.goTo().gotoHomePage();
        }

    }

    @Test
    public void testContactModification() {
        ContactData contactForModification = new ContactData("Modified contact", "Modified contact", "+1430555555", "unique@gmail.com", "group for test contacts");
        List<ContactData> before = app.getContactHelper().getContactList();
        int index = before.size() - 1;

        app.getContactHelper().modify(contactForModification, index);
        app.goTo().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size(), after.size());
        before.remove(index);
        before.add(contactForModification);

        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
