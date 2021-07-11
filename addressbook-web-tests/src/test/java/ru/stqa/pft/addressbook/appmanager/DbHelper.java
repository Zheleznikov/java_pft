package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.ContactSet;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.GroupSet;

import java.util.List;

public class DbHelper {
    private final SessionFactory sessionFactory;

    public DbHelper() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public GroupSet getAllGroups() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery("from GroupData where deprecated = '0000-00-00 00:00:00'").list();
        session.getTransaction().commit();
        session.close();
        return new GroupSet(result);
    }

    public ContactSet getAllContacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00 00:00:00'").list();
        session.getTransaction().commit();
        session.close();
        return new ContactSet(result);
    }

    public ContactData getCurrentContact(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List <ContactData> contacts = session.createQuery("from ContactData where id = " + id).list();
        session.getTransaction().commit();
        session.close();
        return contacts.get(0);
    }

    public GroupData getCurrentGroup(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List <GroupData> groups = session.createQuery("from GroupData where id = " + id).list();
        session.getTransaction().commit();
        session.close();
        return groups.get(0);
    }


}
