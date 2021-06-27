package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static ru.stqa.pft.addressbook.tests.TestBase.app;

public class ContactPhoneTest extends TestBase {

    @Test
    public void testContactPhone() {
        app.goTo().homePage();
        ContactData contact = app.contact().getAll().iterator().next();
        System.out.println(contact);
        app.contact().clickToModify(contact.getEditIcon());
        ContactData contactDataFromEditForm = app.contact().getInfoFromEditForm();
        System.out.println(contactDataFromEditForm);
        System.out.println(contact.equals(contactDataFromEditForm));
    }

    public String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]]", "");
    }
}
