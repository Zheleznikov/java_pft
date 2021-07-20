package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;


public class ResetUserPasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testResetUserPassword() throws IOException {
        Map<String, String> randomUser = null;
        String newPass = "secret pass";
        List<Map<String, String>> allUsers = app.db().getAllUsers();

        if (allUsers.size() == 0)
        {
            System.out.println("Oops");
            System.out.println("No users in mantis, you should add new user manually");
        }
        else {
            app.admin().signInAsAdministrator();
            app.admin().goToManageUsers();
            randomUser = allUsers.get((int) (Math.random() * allUsers.size()));
        }


//        способ получить случайного пользователя через ui
//        randomUser = app.admin().getUserList().removeAdministratorFromList().getRandomUser();

        app.admin().goToManageUser(randomUser);
        app.admin().resetPassword();

        MailMessage mailMessage = app.mail().waitForMail(1, 10000).get(0);
        String confirmationLink = findConfirmationLink(mailMessage);
        app.registration().finish(confirmationLink, newPass);
        assertTrue(app.newSession().login(randomUser.get("name"), newPass));
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

    private String findConfirmationLink(MailMessage mailMessage) {
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @Test
    public void TestTheories() {
        app.admin().signInAsAdministrator();
        app.admin().goToManageUsers();
        app.admin().createUser("userevihw", "userevicwh@mail.com");
        app.admin().goToManageUsers();
    }
}
