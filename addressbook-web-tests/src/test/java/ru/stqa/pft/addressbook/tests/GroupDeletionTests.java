package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @BeforeClass
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().list().size() == 0) {
            GroupData group = new GroupData("group for deleting", "edited unique header", "edited footer");
            app.group().create(group);
            app.goTo().groupPage();
        }
    }

    @Test
    public void testDeletionGroup() throws Exception {
        List<GroupData> before = app.group().list();
        int index = before.size() - 1;

        app.group().delete(index);
        app.goTo().groupPage();


        List<GroupData> after = app.group().list();

        Assert.assertEquals(before.size() - 1, after.size());
        before.remove(index);
        Assert.assertEquals(before, after);
    }



}
