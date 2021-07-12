package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {
    Logger logger = LoggerFactory.getLogger(TestBase.class);


    //    protected static final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);
    protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));


    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method method, Object[] parameters) {
        logger.info("Start test - " + method.getName() + " with parameters " + Arrays.asList(parameters));

    }

    @AfterMethod(alwaysRun = true)
    public void togTestStop(Method method) {
        logger.info("Stop test - " + method.getName());

    }

    public void verifyGroupListInUI() {
//        if (Boolean.getBoolean("verifyUI"))
        {
            GroupSet dbGroups = app.db().getAllGroups();
            GroupSet uiGroups = app.group().getAll();
            assertThat(uiGroups, equalTo(dbGroups.stream().map(g ->
                    new GroupData()
                            .withId(g.getId())
                            .withName(g.getName())
            )
                    .collect(Collectors.toSet())));
        }
    }

    public void createGroup() {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("Group for adding contact2"));
        app.goTo().homePage();
    }

    public void createContact() {
        ContactData contact = new ContactData()
                .withName("contact for adding in group")
                .withLastName("contact for adding in group")
                .withEmail("addingingroup@mail.gmail")
                .withMobilePhone("880050000");

        app.goTo().addNewUserPage();
        app.contact().create(contact);
        app.goTo().homePage();
    }

}
