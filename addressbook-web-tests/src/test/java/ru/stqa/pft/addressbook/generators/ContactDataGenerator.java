package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Important!
 * Don't forget to set working directory as current module in conf
 * E.g. not like this:
 * C:\\Users\\path\\to\\java_pft
 * But
 * C:\\Users\\path\\to\\java_pft\\addressbook-web-tests
 */
public class ContactDataGenerator {

    private final File jsonFile = new File("src/test/resources/contacts.json");
    private final File xmlFile = new File("src/test/resources/contacts.xml");

    @Parameter(names = {"-c", "--contact"}, description = "Contact count")
    public int count = 3; // default value if no arg -c in command line

    @Parameter(names = {"-d", "--dataFormat"}, description = "Data Format")
    public String format = "json"; // default value if no arg -d in command line


    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            jCommander.usage();
            return;
        }
        generator.run();

    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);

        if (format.equals("xml")) {
            saveAsXml(contacts);
        } else if (format.equals("json")) {
            saveAsJson(contacts);
        } else {
            soutAboutUnrecognizedFormat(format);
        }

    }

    private void saveAsJson(List<ContactData> contacts) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        writeFile(jsonFile, json);

    }

    private void saveAsXml(List<ContactData> contacts) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);
        String xml = xStream.toXML(contacts);
        writeFile(xmlFile, xml);
    }

    private void writeFile(File file, String obj) throws IOException {
//        Writer writer = new FileWriter(file);
//        writer.write(obj);
//        writer.close();
        try (Writer writer = new FileWriter(file);) {
            writer.write(obj);
        }
    }

    private void soutAboutUnrecognizedFormat(String format) {
        System.out.println("Unrecognized format " + format);
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int random = new Random().nextInt(1000);
            contacts.add(new ContactData()
                    .withName("Name" + random)
                    .withLastName("Lastname" + random)
                    .withEmail("email@email.com" + random)
                    .withMobilePhone(String.valueOf(random))
                    .withCompanyAddress("Avenue of Hope, " + random)
                    .withGroup("group for test contacts")
//                    .withPhoto(new File("src/test/resources/ava.png"))
                    .withPathToPhoto("src/test/resources/ava.png")
            );
        }
        return contacts;
    }
}
