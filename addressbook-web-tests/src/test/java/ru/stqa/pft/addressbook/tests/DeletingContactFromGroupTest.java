package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletingContactFromGroupTest extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().getAllContacts().size() == 0) {
            createContact();
        }

        if (app.db().getAllGroups().size() == 0) {
            createGroup();
        }
    }

    @Test
    public void testDeletingContactFromGroup() {
        ContactData before = app.db().getAllContacts().iterator().next(); // получили произвольный контакт из БД
        GroupSet contactGroups = before.getGroups(); // получили список его групп

        if (contactGroups.size() == 0) { // если полученный контакт не состоит ни в одной в группе, то
            GroupData groupToAdd = app.db().getAllGroups().iterator().next(); // находим группу для добавления
            app.goTo().homePage();
            app.contact().addContactToGroup(before.getId(), groupToAdd.getId()); // добавляем контакт в группу

            contactGroups.addAll(app.db().getCurrentContact(before.getId()).getGroups()); // добавляем к пустому списку групп исходного контакта полученную группу
            contactGroups.forEach(before::inGroup); // добавляем новую группу к нашему контакту
        }

        GroupData groupToRemove = contactGroups.iterator().next();

        app.goTo().homePage();
        app.contact().removeContactFromGroup(groupToRemove.getId(), before.getId());
        ContactData after = app.db().getCurrentContact(before.getId());
        assertThat(after, equalTo(before.outOfGroup(groupToRemove)));

    }

}
