package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import javax.swing.*;
import java.util.Set;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();

        if (app.contact().list().size() == 0) {
            ContactData contact = new ContactData()
                    .withName("contact for deleting")
                    .withLastName("contact for deleting")
                    .withEmail("deleteme@mail.gmail")
                    .withGroup("group for test contacts")
                    .withMobilePhone("880050000");
            app.goTo().addNewUserPage();
            app.contact().create(contact);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDeletion() {

        Set<ContactData> before = app.contact().getAll();
        ContactData deletedContact = before.iterator().next();

        app.contact().selectContactById(deletedContact.getId());
        app.contact().deleteSelectedContacts();
        app.contact().acceptAlert();
        app.goTo().homePage();

        Set<ContactData> after = app.contact().getAll();

        Assert.assertEquals(before.size() - 1, after.size());
        before.remove(deletedContact);
        Assert.assertEquals(before, after);
    }
}
