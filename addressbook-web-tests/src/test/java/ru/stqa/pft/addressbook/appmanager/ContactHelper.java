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
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        type(By.name("address"), contactData.getCompanyAddress());
//        attach(By.name("photo"), contactData.getPhoto());
        attach(By.name("photo"), contactData.getPathToPhoto());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
        }
        else
        {
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
//        clickToModify(contactForModification.getEditIcon());
        initContactModificationById(contactForModification.getId());
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
            String allPhones = el.findElement(By.xpath("./td[6]")).getText();
            String companyAddress = el.findElement(By.xpath("./td[4]")).getText();
            String allEmails = el.findElement(By.xpath("./td[5]")).getText();
            int id = Integer.parseInt(el.findElement(By.xpath("./td[1]/input")).getAttribute("id"));

            ContactData contact = new ContactData()
                    .withId(id)
                    .withName(name)
                    .withLastName(lastName)
                    .withAllPhones(allPhones)
                    .withAllEmails(allEmails)
                    .withCompanyAddress(companyAddress);
            contactCache.add(contact);
        });

        return contactCache;
    }

    public ContactData getInfoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());

        String name = wd.findElement(By.cssSelector("input[name='firstname']")).getAttribute("value");
        String lastName = wd.findElement(By.cssSelector("input[name='lastname']")).getAttribute("value");
        String email = wd.findElement(By.cssSelector("input[name='email']")).getAttribute("value");
        String email2 = wd.findElement(By.cssSelector("input[name='email2']")).getAttribute("value");
        String email3 = wd.findElement(By.cssSelector("input[name='email3']")).getAttribute("value");
        String workPhone = wd.findElement(By.cssSelector("input[name='work']")).getAttribute("value");
        String mobilePhone = wd.findElement(By.cssSelector("input[name='mobile']")).getAttribute("value");
        String homePhone = wd.findElement(By.cssSelector("input[name='home']")).getAttribute("value");
        String companyAddress = wd.findElement(By.cssSelector("textarea[name='address']")).getAttribute("value");

        return new ContactData()
                .withName(name).withLastName(lastName)
                .withEmail(email).withEmail2(email2).withEmail3(email3)
                .withWorkPhone(workPhone).withHomePhone(homePhone).withMobilePhone(mobilePhone)
                .withCompanyAddress(companyAddress);
    }

    public void initContactModificationById(int id) {
//        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
//        WebElement row = checkbox.findElement(By.xpath("./../.."));
//        List<WebElement> cells = row.findElements(By.tagName("td"));
//        cells.get(7).findElement(By.tagName("a")).click();

//    wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();
//    wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click();

        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void selectGroupToAdd(int id) {
        new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(id));
    }

    public void submitAddingContactToGroup() {
        wd.findElement(By.cssSelector("input[name='add']")).click();
    }

    public void addContactToGroup(int contactId, int groupId) {
        selectContactById(contactId);
        selectGroupToAdd(groupId);
        submitAddingContactToGroup();
    }


}
