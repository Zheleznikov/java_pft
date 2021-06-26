package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().getAll().size() == 0) {
            GroupData groupForCreation = new GroupData().withName("groupForEdit").withHeader("header for edit").withFooter("footer for edit");
            app.group().create(groupForCreation);
            app.goTo().groupPage();
        }

    }

    @Test
    public void testGroupModification() {
        GroupSet before = app.group().getAll();
        GroupData modifiedGroup = before.iterator().next();

        GroupData group = new GroupData()
                .withId(modifiedGroup.getId())
                .withName("edited group")
                .withHeader("edited header")
                .withFooter("edited footer");

        app.group().modify(group);
        app.goTo().groupPage();
        assertThat(app.group().getCount(), equalTo(before.size()));

        GroupSet after = app.group().getAll();
        assertThat(after, equalTo(before
                .without(modifiedGroup)
                .withAdded(group)));
    }


}
