package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getContactHelper().selectContact();
        app.getContactHelper().modificateSelectedContacts();
        app.getContactHelper().fillContactForm(new ContactData("Edited Unique", "edited laast", "+1430555532", "edited2@gmail.com", null), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHomePage();
        app.getSessionHelper().logout();
    }
}
