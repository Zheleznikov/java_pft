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

        if (app.db().getAllContacts().size() == 0) {
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
        ContactSet before = app.db().getAllContacts();
        ContactData modifiedContact = before.iterator().next();

        ContactData contactForModification = new ContactData()
                .withName("modified contact")
                .withLastName("modified contact")
                .withLastName("modified contact")
                .withMobilePhone("7923")
                .withEmail("modifiedEmail@mail.email")
                .withGroup("group for test contacts")
                .withId(modifiedContact.getId());


        app.contact().modify(contactForModification);
        app.goTo().homePage();

        assertThat(app.contact().getCount(), equalTo(before.size()));

        ContactSet after = app.db().getAllContacts();
        System.out.println("Before");
        after.forEach(el -> System.out.println(el));
        System.out.println("After");
        before.forEach(el -> System.out.println(el));

        assertThat(after, equalTo(
                before
                .without(modifiedContact)
                .withAdded(contactForModification)
        ));
    }

}
