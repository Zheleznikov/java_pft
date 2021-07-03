package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.ContactSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object []> xmlValidContacts() throws IOException {
        File xmlFile = new File("src/test/resources/contacts.xml");
        String xml = readFile(xmlFile);
        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
        return contacts.stream().map(c -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> jsonValidContacts() throws IOException {
        File jsonFile = new File("src/test/resources/contacts.json");
        String json = readFile(jsonFile);

        Gson gson = new Gson();
        List<ContactData> groups = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());  // List<ContactData>.class
        return groups.stream().map(c -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "jsonValidContacts")
    public void testContactCreation(ContactData contact) throws Exception {
//        contact.withPhoto(new File("src/test/resources/ava.png"));
        ContactSet before = app.contact().getAll();

        app.goTo().addNewUserPage();
        app.contact().create(contact);
        app.goTo().homePage();

        assertThat(app.contact().getCount(), equalTo(before.size() + 1));
        ContactSet after = app.contact().getAll();

        assertThat(after, equalTo(
                before.withAdded(contact.withId(after
                        .stream()
                        .mapToInt(ContactData::getId)
                        .max().getAsInt()))));
    }

    private String readFile(File file) throws IOException {
        String str = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            str += line;
            line = reader.readLine();
        }
        return str;
    }


}

