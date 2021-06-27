package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTest extends TestBase {

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
