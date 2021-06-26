package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.ContactSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();

        if (app.contact().getAll().size() == 0) {
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
        ContactSet before = app.contact().getAll();
        ContactData deletedContact = before.iterator().next();

        app.contact().selectContactById(deletedContact.getId());
        app.contact().deleteSelectedContacts();
        app.contact().acceptAlert();
        app.goTo().homePage();

        ContactSet after = app.contact().getAll();

        Assert.assertEquals(before.size() - 1, after.size());
        assertThat(after, equalTo(before.without(deletedContact)));
    }
}
