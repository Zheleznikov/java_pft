package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoGroupPage();
        if (!app.getGroupHelper().isThereAGroup()) {
            GroupData groupForCreation = new GroupData("group for edit", "edited header", "edited footer");
            app.getGroupHelper().createGroup(groupForCreation);
            app.getNavigationHelper().gotoGroupPage();
        }

    }

    @Test
    public void testGroupModification() {
        List <GroupData> before = app.getGroupHelper().getGroupList();
        int index = before.size() - 1;

        // оставил как пример, что можно передаваь id сюда, хоть это и необязталеьно
        // можно объявить экземпляр класса GroupData в начале метода
        GroupData group = new GroupData(before.get(index).getId(), "edited group", "edited unique header", "edited footer");

        app.getGroupHelper().modifyGroup(index, group);
        app.getNavigationHelper().gotoGroupPage();

        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(before.size(), after.size());
        before.remove(index);
        before.add(group);

        Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }


}
