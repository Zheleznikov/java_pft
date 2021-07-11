package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Псевдокод
 *
 * предусловие
 * 1. Если контактов нет, то создаем новый контакт
 * 2. Создаем новую группу
 *
 * ТЕСТ
 * 1. Получаем любой контакт из БД
 * 2. Получаем созданную в предусловии группу из БД
 * 3. Добавляем контакт в группу через UI
 * 4. Получаем данные нового контакта из БД
 * 5. Сравниваем
 *
 * постусловие
 * 1. Удаляем созданную группу
 */
public class AddingContactToGroupTest extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().getAllContacts().size() == 0) {
            ContactData contact = new ContactData()
                    .withName("contact for adding in group")
                    .withLastName("contact for adding in group")
                    .withEmail("addingingroup@mail.gmail")
                    .withMobilePhone("880050000");
            app.goTo().addNewUserPage();
            app.contact().create(contact);
            app.goTo().homePage();
        }


        app.goTo().groupPage();
        app.group().create(new GroupData().withName("Group for adding contact"));
        app.goTo().homePage();


    }

    @Test
    public void testAddingContactToGroup() {

        ContactData randomContactToAddingInGroup = app.db().getAllContacts().iterator().next();
        GroupSet groupsFromDb = app.db().getAllGroups();
        GroupData group = app.db().getCurrentGroup(
                groupsFromDb.stream()
                        .mapToInt(GroupData::getId)
                        .max().getAsInt()
        );


        app.goTo().homePage();
        app.contact().addContactToGroup(randomContactToAddingInGroup.getId(), group.getId());

        ContactData contactFromDb = app.db().getCurrentContact(randomContactToAddingInGroup.getId());

        assertThat(contactFromDb, equalTo(
                randomContactToAddingInGroup.inGroup(group)
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


}
