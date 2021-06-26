package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.ContactSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        ContactData contact = new ContactData()
                .withName("new contact")
                .withLastName("new contact")
                .withMobilePhone("+7900")
                .withEmail("email@mail.mail")
                .withGroup("group for test contacts");

        ContactSet before = app.contact().getAll();

        app.goTo().addNewUserPage();
        app.contact().create(contact);
        app.goTo().homePage();

        ContactSet after = app.contact().getAll();

        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after
                        .stream()
                        .mapToInt(ContactData::getId)
                        .max().getAsInt()))));
    }
}

