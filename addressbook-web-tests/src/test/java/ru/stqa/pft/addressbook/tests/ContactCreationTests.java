package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        ContactData contact = new ContactData()
                .withName("new contact")
                .withLastName("new contact")
                .withMobilePhone("+7900")
                .withEmail("email@mail.mail")
                .withGroup("group for test contacts");

        Set<ContactData> before = app.contact().getAll();

        app.goTo().addNewUserPage();
        app.contact().create(contact);
        app.goTo().homePage();

        Set<ContactData> after = app.contact().getAll();

        contact.withId(
                after.stream().mapToInt(g -> g.getId()).max().getAsInt());

        Assert.assertEquals(before.size() + 1, after.size());

        before.add(contact);
        Assert.assertEquals(before, after);
    }
}

