package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import java.util.List;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void submitCreation() {
        click(By.name("submit"));
    }

    public void submitModification() {
        click(By.name("update"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }


    public void initNewGroupCreation() {
        click(By.name("new"));
    }


    public void deleteSelectedGroups() {
        click(By.name("delete"));
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void create(GroupData group) {
        initNewGroupCreation();
        fillGroupForm(group);
        submitCreation();
        groupCache = null;
    }

    public void modify(GroupData group) {
        selectById(group.getId());
        initGroupModification();
        fillGroupForm(group);
        submitModification();
        groupCache = null;
    }

    public boolean isThereAGroup() {
        System.out.println("No element");
        return isElementPresent(By.name("selected[]"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private GroupSet groupCache = null;

    public GroupSet getAll() {
        if (groupCache != null) {
            return new GroupSet(groupCache);
        }

        groupCache = new GroupSet();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        elements.forEach(el -> {
            int id = Integer.parseInt(el.findElement(By.tagName("input")).getAttribute("value"));
            GroupData group = new GroupData().withId(id).withName(el.getText());
            groupCache.add(group);
        });

        return new GroupSet(groupCache);
    }


    public void delete(GroupData group) {
        selectById(group.getId());
        deleteSelectedGroups();
        groupCache = null;
    }

    private void selectById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }
}
