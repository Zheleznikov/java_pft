package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {


    @Test
    public void testGroupCreation() throws Exception {
        GroupData group = new GroupData().withName("group for test contacts").withHeader("header").withFooter("footer");

        app.goTo().groupPage();
        GroupSet before = app.group().getAll();
        app.group().create(group);
        app.goTo().groupPage();

        GroupSet after = app.group().getAll();

        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(
                before.withAdded(group.withId(after
                        .stream()
                        .mapToInt(GroupData::getId)
                        .max().getAsInt()))));


    }


}
