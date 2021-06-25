package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @BeforeClass
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isThereAGroup()) {
            GroupData group = new GroupData("group for deleting", "edited unique header", "edited footer");
            app.getGroupHelper().createGroup(group);
            app.getNavigationHelper().gotoGroupPage();
        }
    }

    @Test
    public void testDeletionGroup() throws Exception {
        List<GroupData> before = app.getGroupHelper().getGroupList();
        int index = before.size() - 1;

        app.getGroupHelper().selectGroup(index);
        app.getGroupHelper().deleteSelectedGroups();
        app.getNavigationHelper().gotoGroupPage();

        List<GroupData> after = app.getGroupHelper().getGroupList();

        Assert.assertEquals(index, after.size());
        before.remove(index);
        Assert.assertEquals(before, after);
    }


}
