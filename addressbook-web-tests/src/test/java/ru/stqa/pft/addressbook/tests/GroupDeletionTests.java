package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupDeletionTests extends TestBase {

    @BeforeClass
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            GroupData group = new GroupData().withName("group for deleting");
            app.group().create(group);
            app.goTo().groupPage();
        }
    }

    @Test
    public void testDeletionGroup() throws Exception {
        Set<GroupData> before = app.group().all();
        GroupData deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);
        app.goTo().groupPage();
        Set<GroupData> after = app.group().all();

        Assert.assertEquals(before.size() - 1, after.size());
        before.remove(deletedGroup);
        Assert.assertEquals(before, after);
    }



}
