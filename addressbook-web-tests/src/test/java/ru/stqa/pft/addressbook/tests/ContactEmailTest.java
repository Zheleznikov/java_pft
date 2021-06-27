package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().getAll().size() == 0) {
            ContactData contact = new ContactData()
                    .withName("name - address Test")
                    .withLastName("last name - address Test")
                    .withGroup("group for test contacts")
                    .withMobilePhone("880050000")
                    .withCompanyAddress("Avenue of hope")
                    .withEmail("first@e.mail")
                    .withEmail2("secondt@e.mail")
                    .withEmail3("third@e.mail");
            app.goTo().addNewUserPage();
            app.contact().create(contact);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactEmail() {
        ContactData contact = app.contact().getAll().iterator().next();
        app.contact().clickToModify(contact.getEditIcon());
        ContactData contactDataFromEditForm = app.contact().getInfoFromEditForm();

        assertThat(contact.getAllEmails(), equalTo(
                mergeEmail(contactDataFromEditForm)
        ));

    }

    private String mergeEmail(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream()
                .filter(str -> !str.equals("")).collect(Collectors.joining("\n"));
    }
}
