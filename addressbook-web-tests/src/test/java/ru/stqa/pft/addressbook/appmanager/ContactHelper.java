package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitCreation() {
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

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
    }


    public void modifySelectedContacts(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void submitModification() {
        click(By.name("update"));
    }

    public void create(ContactData contact) {
        fillContactForm(contact, true);
        submitCreation();
    }


    public void modify(ContactData contactForModification, int index) {
        modifySelectedContacts(index);
        fillContactForm(contactForModification, false);
        submitModification();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']//tr[@name='entry']"));
        elements.forEach(el-> {
            String lastName = el.findElement(By.xpath("./td[2]")).getText();
            String name = el.findElement(By.xpath("./td[3]")).getText();
            int id = Integer.parseInt(el.findElement(By.xpath("./td[1]/input")).getAttribute("id"));
            ContactData contact = new ContactData()
                    .withId(id)
                    .withName(name)
                    .withLastName(lastName);
            contacts.add(contact);
        });

        return contacts;
    }

    public Set <ContactData> getAll() {
        Set<ContactData> contacts = new HashSet<>();
        List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']//tr[@name='entry']"));
        elements.forEach(el-> {
            String lastName = el.findElement(By.xpath("./td[2]")).getText();
            String name = el.findElement(By.xpath("./td[3]")).getText();
            int id = Integer.parseInt(el.findElement(By.xpath("./td[1]/input")).getAttribute("id"));
            ContactData contact = new ContactData()
                    .withId(id)
                    .withName(name)
                    .withLastName(lastName);
            contacts.add(contact);
        });

        return contacts;
    }
}
