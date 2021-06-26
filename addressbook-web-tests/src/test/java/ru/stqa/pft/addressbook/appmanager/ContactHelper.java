package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.ContactSet;

import java.util.List;

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

    public void clickToModify(WebElement editIcon) {
        editIcon.click();
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
        contactCache = null;
    }


    public void modify(ContactData contactForModification) {
        clickToModify(contactForModification.getEditIcon());
        fillContactForm(contactForModification, false);
        submitModification();
        contactCache = null;
    }


    public void delete(ContactData deletedContact) {
        selectContactById(deletedContact.getId());
        deleteSelectedContacts();
        acceptAlert();
        contactCache = null;
    }

    public int getCount() {
        return wd.findElements(By.xpath("//table[@id='maintable']//tr[@name='entry']")).size();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    private ContactSet contactCache = null;

    public ContactSet getAll() {
        if (contactCache != null) {
            return contactCache;
        }
        contactCache = new ContactSet();
        List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']//tr[@name='entry']"));
        elements.forEach(el -> {
            String lastName = el.findElement(By.xpath("./td[2]")).getText();
            String name = el.findElement(By.xpath("./td[3]")).getText();
            int id = Integer.parseInt(el.findElement(By.xpath("./td[1]/input")).getAttribute("id"));
            WebElement editIcon = el.findElement(By.xpath(".//img[@alt='Edit']"));

            ContactData contact = new ContactData()
                    .withId(id)
                    .withName(name)
                    .withLastName(lastName)
                    .withEditIcon(editIcon);
            contactCache.add(contact);
        });

        return contactCache;
    }
}
