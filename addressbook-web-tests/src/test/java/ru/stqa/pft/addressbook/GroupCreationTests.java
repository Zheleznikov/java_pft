package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import org.openqa.selenium.*;

public class GroupCreationTests {
    private WebDriver wd;


    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wd.get("http://localhost/addressbook/");
        login();
    }

    @Test
    public void testGroupCreation() throws Exception {
        gotoGroupPage();
        initNewGroupCreation();
        fillGroupForm();
        submitGroupCreation();
        gotoGroupPage();
        logout();
    }

    private void submitGroupCreation() {
        wd.findElement(By.name("submit")).click();
    }

    private void login() {
        wd.findElement(By.name("user")).clear();
        wd.findElement(By.name("user")).sendKeys("admin");
        wd.findElement(By.name("pass")).clear();
        wd.findElement(By.name("pass")).sendKeys("secret");
        wd.findElement(By.xpath("//input[@value='Login']")).click();
    }

    private void logout() {
        wd.findElement(By.linkText("Logout")).click();
    }

    private void fillGroupForm() {
        wd.findElement(By.name("group_header")).sendKeys("group header");
        wd.findElement(By.name("group_name")).sendKeys("group first");
        wd.findElement(By.name("group_footer")).sendKeys("group footer");
    }

    private void initNewGroupCreation() {
        wd.findElement(By.name("new")).click();
    }

    private void gotoGroupPage() {
        wd.findElement(By.linkText("groups")).click();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        wd.quit();

    }

    private boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }


}
