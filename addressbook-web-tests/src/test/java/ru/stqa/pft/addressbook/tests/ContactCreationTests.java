package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        ContactData contact = new ContactData("new contact", "new contact", "+1430555555", "unique@gmail.com", "group for test contacts");

        List<ContactData> before = app.getContactHelper().getContactList();

        app.getNavigationHelper().gotoAddNewUserPage();
        app.getContactHelper().createContact(contact);
        app.getNavigationHelper().gotoHomePage();

        List<ContactData> after = app.getContactHelper().getContactList();

        app.getContactHelper().getContactList();

        Assert.assertEquals(before.size() + 1, after.size());
        before.add(contact);
        Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o1.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}

