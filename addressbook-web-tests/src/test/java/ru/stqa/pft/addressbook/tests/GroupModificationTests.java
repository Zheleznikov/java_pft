package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase{
    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData("edited group", "edited unique header", "edited footer"));
        app.getGroupHelper().submitGroupModification();
        app.getNavigationHelper().gotoGroupPage();
        app.getSessionHelper().logout();
    }
}
