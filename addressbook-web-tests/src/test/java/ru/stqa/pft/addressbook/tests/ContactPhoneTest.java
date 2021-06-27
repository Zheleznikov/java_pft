package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().getAll().size() == 0) {
            ContactData contact = new ContactData()
                    .withName("name - phones Test")
                    .withLastName("last name - phones Test")
                    .withGroup("group for test contacts")
                    .withMobilePhone("+7 (900) 12")
                    .withWorkPhone("211-56-83")
                    .withHomePhone("880050");
            app.goTo().addNewUserPage();
            app.contact().create(contact);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactPhone() {
        app.goTo().homePage();
        ContactData contact = app.contact().getAll().iterator().next();
        app.contact().clickToModify(contact.getEditIcon());
        ContactData contactDataFromEditForm = app.contact().getInfoFromEditForm();

        assertThat(contact.getAllPhones(), equalTo(
                mergePhones(contactDataFromEditForm)
        ));

    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream()
                .map(this::cleaned)
                .filter(str -> !str.equals("")).collect(Collectors.joining("\n"));
    }

    public String cleaned(String phone) {
        return phone.replaceAll("[\\s-()]", "");
    }
}
