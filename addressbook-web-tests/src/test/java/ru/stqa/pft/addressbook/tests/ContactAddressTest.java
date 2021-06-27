package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().getAll().size() == 0) {
            ContactData contact = new ContactData()
                    .withName("name - address Test")
                    .withLastName("last name - address Test")
                    .withGroup("group for test contacts")
                    .withMobilePhone("880050000")
                    .withCompanyAddress("Avenue of hope");
            app.goTo().addNewUserPage();
            app.contact().create(contact);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactAddress() {
        ContactData contact = app.contact().getAll().iterator().next();
        app.contact().clickToModify(contact.getEditIcon());
        ContactData contactDataFromEditForm = app.contact().getInfoFromEditForm();

        assertThat(contact.getCompanyAddress(), equalTo(
                contactDataFromEditForm.getCompanyAddress()
        ));
    }
}
