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
                .withName("name - new contact")
                .withLastName("lastName - new contact")
                .withMobilePhone("+7900")
                .withHomePhone("+7(900)-23")
                .withWorkPhone("555 55 00")
                .withEmail("email@mail.mail")
                .withGroup("group for test contacts")
                .withCompanyAddress("postcard address");

        ContactSet before = app.contact().getAll();

        app.goTo().addNewUserPage();
        app.contact().create(contact);
        app.goTo().homePage();

        assertThat(app.contact().getCount(), equalTo(before.size() + 1));
        ContactSet after = app.contact().getAll();

        assertThat(after, equalTo(
                before.withAdded(contact.withId(after
                        .stream()
                        .mapToInt(ContactData::getId)
                        .max().getAsInt()))));
    }
}

