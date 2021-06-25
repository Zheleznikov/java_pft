package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {


    @Test
    public void testGroupCreation() throws Exception {
        GroupData group = new GroupData("group for test contacts", "some header2", "some footer2");

        app.goTo().groupPage();
        List<GroupData> before = app.group().list();

        app.group().create(group);
        app.goTo().groupPage();

        List<GroupData> after = app.group().list();
        Assert.assertEquals(after.size(), before.size() + 1);
        before.add(group);
        Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }


}
