package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroups() throws IOException {
        List<Object[]> list = new ArrayList<>();

//        list.add(new Object[] {new GroupData().withName("test name 1").withHeader("test header 1").withFooter("test footer1")});
//        list.add(new Object[] {new GroupData().withName("test name 2").withHeader("test header 2").withFooter("test footer2")});
//        list.add(new Object[] {new GroupData().withName("test name 3").withHeader("test header 3").withFooter("test footer3")});

//        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv")));
//        String line = reader.readLine();
//        while (line != null) {
//            String [] split = line.split(";");
//            list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[1])});
//            line = reader.readLine();
//        }
//        return list.iterator();

        String xml = "";
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")));
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }

        XStream xsteram = new XStream();
        xsteram.processAnnotations(GroupData.class);
        List<GroupData> groups = (List<GroupData>) xsteram.fromXML(xml);
        return groups.stream().map(g -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }


    @Test(dataProvider = "validGroups")
    public void testGroupCreation(GroupData group) throws Exception {
//        GroupData group = new GroupData().withName("group for test contacts").withHeader("header").withFooter("footer");

        app.goTo().groupPage();
        GroupSet before = app.group().getAll();
        app.group().create(group);
        app.goTo().groupPage();

        assertThat(app.group().getCount(), equalTo(before.size() + 1));

        GroupSet after = app.group().getAll();

        assertThat(after, equalTo(
                before.withAdded(group.withId(after
                        .stream()
                        .mapToInt(GroupData::getId)
                        .max().getAsInt()))));
    }
}
