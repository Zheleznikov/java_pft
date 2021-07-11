package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import javax.swing.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Псевдокод
 * <p>
 * предусловие
 * 1. Если контактов нет, то создаем новый контакт
 * 2. Создаем новую группу
 * <p>
 * ТЕСТ
 * 1. Получаем любой контакт из БД
 * 2. Получаем созданную в предусловии группу из БД
 * 3. Добавляем контакт в группу через UI
 * 4. Получаем данные нового контакта из БД
 * 5. Сравниваем
 * <p>
 * постусловие
 * 1. Удаляем созданную группу
 */
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
        ContactData randomContactToAddingInGroup = app.db().getAllContacts().iterator().next(); // получили произвольный контакт из БД
        GroupSet groupsFromDb = app.db().getAllGroups(); // создали сет из групп на основе групп из БД
        GroupSet groupsFromContact = randomContactToAddingInGroup.getGroups(); // создали сет из групп на основе тех, что связаны с контактом
        groupsFromDb.removeAll(groupsFromContact); // оставили в сете групп из БД только те группы, которых нет в списке групп контакта

        if (groupsFromDb.size() == 0) { // если к контакту привязаны все имеющиеся в БД группы, то
            createGroup();
            groupsFromDb.addAll(app.db().getAllGroups());
        }

        GroupData groupToAdd = groupsFromDb.iterator().next();

        app.goTo().homePage();
        app.contact().addContactToGroup(randomContactToAddingInGroup.getId(), groupToAdd.getId());

        ContactData contactFromDb = app.db().getCurrentContact(randomContactToAddingInGroup.getId());

        assertThat(contactFromDb, equalTo(
                randomContactToAddingInGroup.inGroup(groupToAdd)
        ));
    }

    private void createGroup() {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("Group for adding contact2"));
        app.goTo().homePage();
    }

    private void createContact() {
        ContactData contact = new ContactData()
                .withName("contact for adding in group")
                .withLastName("contact for adding in group")
                .withEmail("addingingroup@mail.gmail")
                .withMobilePhone("880050000");
        app.goTo().homePage();
        app.goTo().addNewUserPage();
        app.contact().create(contact);
        app.goTo().homePage();
    }


}
