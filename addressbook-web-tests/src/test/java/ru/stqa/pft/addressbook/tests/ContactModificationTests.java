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
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            ContactData contactForCreation = new ContactData("Contact for modify", "Contact for modify", "+1430555555", "unique@gmail.com", "group for test contacts");
            app.goTo().addNewUserPage();
            app.contact().create(contactForCreation);
            app.goTo().homePage();
        }

    }

    @Test
    public void testContactModification() {
        ContactData contactForModification = new ContactData("Modified contact", "Modified contact", "+1430555555", "unique@gmail.com", "group for test contacts");
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;

        app.contact().modify(contactForModification, index);
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(before.size(), after.size());
        before.remove(index);
        before.add(contactForModification);

        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
