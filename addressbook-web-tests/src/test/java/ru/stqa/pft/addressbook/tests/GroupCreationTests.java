package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupCreationTests extends TestBase {


    @Test
    public void testGroupCreation() throws Exception {
        GroupData group = new GroupData().withName("group for test contacts").withHeader("header").withFooter("footer");

        app.goTo().groupPage();
        Set<GroupData> before = app.group().all();
        app.group().create(group);
        app.goTo().groupPage();

        Set<GroupData> after = app.group().all();
        Assert.assertEquals(after.size(), before.size() + 1);
        group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt());
        before.add(group);
        Assert.assertEquals(before, after);
    }


}
