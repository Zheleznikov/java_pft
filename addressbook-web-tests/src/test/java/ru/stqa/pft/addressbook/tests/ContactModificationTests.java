package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().getAll().size() == 0) {
            ContactData contactForCreation = new ContactData()
                    .withName("contact for modify")
                    .withLastName("contact  for modify")
                    .withEmail("modifyme@mail.mail")
                    .withGroup("group for test contacts")
                    .withMobilePhone("+7900");
            app.goTo().addNewUserPage();
            app.contact().create(contactForCreation);
            app.goTo().homePage();
        }

    }

    @Test
    public void testContactModification() {
        Set<ContactData> before = app.contact().getAll();
        ContactData modifiedContact = before.iterator().next();

        ContactData contactForModification = new ContactData()
                .withName("modified contact")
                .withLastName("modified contact")
                .withLastName("modified contact")
                .withMobilePhone("7923")
                .withEmail("modifiedEmail@mail.email")
                .withGroup("group for test contacts")
                .withId(modifiedContact.getId())
                .withEditIcon(modifiedContact.getEditIcon());


        app.contact().modify(contactForModification);
        app.goTo().homePage();
        Set<ContactData> after = app.contact().getAll();
        Assert.assertEquals(before.size(), after.size());
        before.remove(modifiedContact);
        before.add(contactForModification);

        Assert.assertEquals(before, after);
    }

}
