package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.ContactSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
        ContactSet before = app.contact().getAll();
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

        assertThat(app.contact().getCount(), equalTo(before.size()));

        ContactSet after = app.contact().getAll();

        assertThat(after, equalTo(
                before
                .without(modifiedContact)
                .withAdded(contactForModification)
        ));
    }

}
