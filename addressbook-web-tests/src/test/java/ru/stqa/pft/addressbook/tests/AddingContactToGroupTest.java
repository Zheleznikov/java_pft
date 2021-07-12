package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class AddingContactToGroupTest extends TestBase {


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
    public void testAddingContactToGroup() {
        ContactData before = app.db().getAllContacts().iterator().next(); // получили произвольный контакт из БД
        GroupSet groupsFromDb = app.db().getAllGroups(); // создали сет из групп на основе групп из БД
        GroupSet groupsFromContact = before.getGroups(); // создали сет из групп на основе тех, что связаны с контактом
        groupsFromDb.removeAll(groupsFromContact); // оставили в сете групп из БД только те группы, которых нет в списке групп контакта

        if (groupsFromDb.size() == 0) { // если к контакту привязаны все имеющиеся в БД группы, то
            createGroup();
            groupsFromDb.addAll(app.db().getAllGroups());
        }

        GroupData groupToAdd = groupsFromDb.iterator().next();

        app.goTo().homePage();
        app.contact().addContactToGroup(before.getId(), groupToAdd.getId());

        ContactData after = app.db().getCurrentContact(before.getId());

        assertThat(after, equalTo(
                before.inGroup(groupToAdd)
        ));
    }




}
