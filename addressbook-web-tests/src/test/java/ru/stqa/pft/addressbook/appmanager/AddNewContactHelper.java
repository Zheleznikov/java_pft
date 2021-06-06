package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class AddNewContactHelper {
    private ChromeDriver wd;
    public AddNewContactHelper(ChromeDriver wd) {
        this.wd = wd;
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    }

    public void fillContactForm(ContactData contactData) {
        wd.findElement(By.name("firstname")).sendKeys(contactData.getName());
        wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
        wd.findElement(By.name("mobile")).sendKeys(contactData.getMobilePhone());
        wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
    }
}
