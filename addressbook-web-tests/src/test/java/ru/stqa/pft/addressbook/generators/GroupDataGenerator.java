package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    @Parameter(names = {"-c", "--count"}, description = "Group count")
    public int count = 5;

    @Parameter(names = {"-f", "--file"}, description = "Target file")
    public String file = "src/test/resources/groups.json";

    @Parameter(names = {"-d", "--dataFormat"}, description = "Data format")
    public String format = "json";

    public static void main(String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
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
        List<GroupData> groups = genetateGroups(count);
        if (format.equals("csv")) {
            saveAsCsv(groups, new File(file));
        } else if (format.equals("xml")) {
            saveAsXml(groups, new File(file));
        } else if (format.equals("json")) {
            saveAsJson(groups, new File(file));
        } else {
            System.out.println("Unrecognized format " + format);
        }
    }

    private void saveAsXml(List<GroupData> groups, File file) throws IOException {
        XStream xstream = new XStream();
//        xstream.alias("group", GroupData.class);
        xstream.processAnnotations(GroupData.class);
        String xml = xstream.toXML(groups);
//        Writer writer = new FileWriter(file);
//        writer.write(xml);
//        writer.close();
        try (Writer writer = new FileWriter(file)) {
            writer.write(xml);
        }
    }

    private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
//        Writer writer = new FileWriter(file);
//        groups.forEach(group -> {
//            try {
//                writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        writer.close();

        try (Writer writer = new FileWriter(file)) {
            groups.forEach(group -> {
                try {
                    writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void saveAsJson(List<GroupData> groups, File file) throws IOException {
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(groups);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }
    }

    private List<GroupData> genetateGroups(int count) {
        List<GroupData> groups = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            groups.add(
                    new GroupData()
                            .withName("group for test contacts")
                            .withHeader(String.format("test group header - %s", i))
                            .withFooter(String.format("test group footer - %s", i))
            );
        }
        return groups;
    }
}
