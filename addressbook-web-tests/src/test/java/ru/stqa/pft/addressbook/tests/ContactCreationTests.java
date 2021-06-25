package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        ContactData contact = new ContactData()
                .withName("new contact")
                .withLastName("new contact")
                .withMobilePhone("+7900")
                .withEmail("email@mail.mail")
                .withGroup("group for test contacts");
        List<ContactData> before = app.contact().list();

        app.goTo().addNewUserPage();
        app.contact().create(contact);
        app.goTo().homePage();

        List<ContactData> after = app.contact().list();

        app.contact().list();

        Assert.assertEquals(before.size() + 1, after.size());
        before.add(contact);
        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o1.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}

