package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().gotoAddNewUserPage();
        app.getAddNewContactHelper().fillContactForm(new ContactData("Unique", "New-User-Original", "+1430555555", "unique@gmail.com"));
        app.getAddNewContactHelper().submitContactCreation();
        app.getNavigationHelper().gotoHomePage();
        app.getSessionHelper().logout();
    }
}

