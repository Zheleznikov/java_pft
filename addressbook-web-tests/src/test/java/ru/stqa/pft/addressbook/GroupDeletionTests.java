package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;

public class GroupDeletionTests extends TestBase {

    @Test
    public void testDeletionGroup() throws Exception {
        gotoGroupPage();
        selectGroup();
        deleteSelectedGroups();
        gotoGroupPage();
    }


}
