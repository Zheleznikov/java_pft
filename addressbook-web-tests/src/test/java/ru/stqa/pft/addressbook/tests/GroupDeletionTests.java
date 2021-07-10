package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

    @BeforeClass
    public void ensurePreconditions() {
        app.goTo().groupPage();

        if (app.db().getAllGroups().size() == 0) {
            GroupData group = new GroupData().withName("group for deleting");
            app.group().create(group);
            app.goTo().groupPage();
        }
    }

    @Test
    public void testDeletionGroup() throws Exception {
        GroupSet before = app.db().getAllGroups();
        GroupData deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);
        app.goTo().groupPage();
        assertThat(app.group().getCount(), equalTo(before.size() - 1));
        GroupSet after = app.db().getAllGroups();


        assertThat(after, equalTo(before.without(deletedGroup)));

        verifyGroupListInUI();

    }



}
