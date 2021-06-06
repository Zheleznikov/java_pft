package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class GroupCreationTests extends TestBase {


    @Test
    public void testGroupCreation() throws Exception {
        app.gotoGroupPage();
        app.initNewGroupCreation();
        app.fillGroupForm(new GroupData("new group", "unique header", "never used footer"));
        app.submitGroupCreation();
        app.gotoGroupPage();
        app.logout();
    }


}
