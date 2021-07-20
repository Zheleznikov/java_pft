package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdministratorHelper extends HelperBase {

    private List<Map<String, String>> userListFromUi = new ArrayList<>();


    public AdministratorHelper(ApplicationManager app) {
        super(app);
    }

    public void signInAsAdministrator() {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), app.getProperty("web.adminLogin"));
        click(By.className("btn-success"));
        type(By.name("password"), app.getProperty("web.pass"));
        click(By.className("btn-success"));
    }

    public void goToManageUsers() {
        wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    }

    public AdministratorHelper getUserList() {
        List<WebElement> usersInUi = wd.findElements(By.xpath("//table//tbody/tr"));
        usersInUi.forEach(user -> {
            String name = user.findElement(By.xpath("./td[1]/a")).getText();
            userListFromUi.add(Map.of(
                    "name", name,
                    "email", user.findElement(By.xpath("./td[3]")).getText()
            ));
        });
        return this;
    }

//    public List<Map<String, String>> removeAdministratorFromList() {
//        return userListFromUi.stream().filter(user -> !user.get("name").equals(app.getProperty("web.adminLogin"))).collect(Collectors.toList());
//    }

    public AdministratorHelper removeAdministratorFromList() {
        this.userListFromUi = userListFromUi.stream().filter(user -> !user.get("name").equals(app.getProperty("web.adminLogin"))).collect(Collectors.toList());
        return this;
    }

    public Map<String, String> getRandomUser() {
        System.out.println(userListFromUi.size());
        return userListFromUi.get((int) (Math.random() * userListFromUi.size()));
    }


    public void goToManageUser(Map<String, String> user) {
        String locator = String.format("//table//tbody//tr//td[1]/a[contains(text(), '%s')]", user.get("name"));
        click(By.xpath(locator));
    }

    public void resetPassword() {
        click(By.cssSelector("input[value='Reset Password'"));
    }

    public void finish(String confirmationLink, String newPass) {
        wd.get(confirmationLink);
        type(By.name("password"), newPass);
        type(By.name("password_confirm"), newPass);
        click(By.className("btn-success"));
    }

    public void createUser(String username, String email) {
        click(By.xpath("//a[contains(@class, 'btn btn-primary btn-white btn-round btn-sm')]"));
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Create User']"));
    }
}
