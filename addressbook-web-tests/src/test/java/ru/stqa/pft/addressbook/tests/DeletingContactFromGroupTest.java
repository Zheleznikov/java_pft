package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Предулосвие
 * 1. Создать группу
 * тест
 * 2. Добавить в эту группу контакт
 * 3. Зафиксировать контакт
 * 4. Удалить контакт
 * 5. Проверить
 *
 * Постусловие
 * Удалить группу
 */
public class DeletingContactFromGroupTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().getAllContacts().size() == 0) {
            ContactData contact = new ContactData()
                    .withName("contact for removing in group")
                    .withLastName("contact for removing in group")
                    .withEmail("addingingroup@mail.gmail")
                    .withMobilePhone("880050000");
            app.goTo().addNewUserPage();
            app.contact().create(contact);
            app.goTo().homePage();
        }

        app.goTo().groupPage();
        app.group().create(new GroupData().withName("Group for adding and removing contact"));
        app.goTo().homePage();



    }

    @Test
    public void testDeletingContactFromGroup() {
        Map<String, Object> data = addContactToGroup();
        GroupData group = (GroupData) data.get("group");
        ContactData randomContactToAddingInGroup = (ContactData) data.get("contact");


        ContactData currentContact = app.db().getCurrentContact(randomContactToAddingInGroup.getId());

        app.goTo().homePage();

        app.contact().removeContactFromGroup(group.getId(), randomContactToAddingInGroup.getId());

        ContactData contactFromDb = app.db().getCurrentContact(randomContactToAddingInGroup.getId());

        assertThat(contactFromDb, equalTo(
                currentContact.outOfGroup(group)
        ));

    }



    @AfterMethod
    public void ensurePostconditions() {
        GroupSet groupsFromDb = app.db().getAllGroups();
        GroupData deletedGroup = app.db().getCurrentGroup(
                groupsFromDb.stream()
                        .mapToInt(GroupData::getId)
                        .max().getAsInt()
        );
        app.goTo().groupPage();
        app.group().delete(deletedGroup);
    }


    /**
     * Метод чтобы добавить в группу контакт для дальнешего удаления
     * @return
     * возвращает таблицу с данными о группе и контакте чтобы в дальнейшем пользоваться ими в тесте
     */
    public Map<String, Object> addContactToGroup() {
        ContactData randomContactToAddingInGroup = app.db().getAllContacts().iterator().next();
        GroupSet groupsFromDb = app.db().getAllGroups();
        GroupData group = app.db().getCurrentGroup(
                groupsFromDb.stream()
                        .mapToInt(GroupData::getId)
                        .max().getAsInt()
        );
        app.goTo().homePage();
        app.contact().addContactToGroup(randomContactToAddingInGroup.getId(), group.getId());


        Map<String, Object> data = new HashMap<>();
        data.put("contact", randomContactToAddingInGroup);
        data.put("group", group);
        return data;
    }
}
