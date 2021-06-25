package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
    }


    public void modificateSelectedContacts(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void createContact(ContactData contact) {
        fillContactForm(contact, true);
        submitContactCreation();
    }


    public void modify(ContactData contactForModification, int index) {
        modificateSelectedContacts(index);
        fillContactForm(contactForModification, false);
        submitContactModification();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']//tr[@name='entry']"));
        elements.forEach(el-> {
            String lastName = el.findElement(By.xpath("./td[2]")).getText();
            String name = el.findElement(By.xpath("./td[3]")).getText();
            int id = Integer.parseInt(el.findElement(By.xpath("./td[1]/input")).getAttribute("id"));
            ContactData contact = new ContactData(id, name, lastName, null, null, null);
            contacts.add(contact);
        });

        return contacts;
    }
}
