package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import org.openqa.selenium.*;

public class GroupCreationTests extends TestBase {


    @Test
    public void testGroupCreation() throws Exception {
        gotoGroupPage();
        initNewGroupCreation();
        fillGroupForm(new GroupData("new group", "unique header", "never used footer"));
        submitGroupCreation();
        gotoGroupPage();
        logout();
    }


}
