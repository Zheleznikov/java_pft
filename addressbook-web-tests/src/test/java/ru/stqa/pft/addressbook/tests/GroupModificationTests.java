package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            GroupData groupForCreation = new GroupData().withName("groupForEdit").withHeader("header for edit").withFooter("footer for edit");
            app.group().create(groupForCreation);
            app.goTo().groupPage();
        }

    }

    @Test
    public void testGroupModification() {
        Groups before = app.group().all();
        GroupData modifiedGroup = before.iterator().next();

        // оставил как пример, что можно передаваь id сюда, хоть это и необязталеьно
        // можно объявить экземпляр класса GroupData в начале метода
        GroupData group = new GroupData()
                .withId(modifiedGroup.getId())
                .withName("edited group")
                .withHeader("edited header")
                .withFooter("edited footer");

        app.group().modify(group);
        app.goTo().groupPage();

        Groups after = app.group().all();
        assertThat(before.size(), equalTo(after.size()));
        assertThat(after, equalTo(before
                .without(modifiedGroup)
                .withAdded(group)));
    }


}
