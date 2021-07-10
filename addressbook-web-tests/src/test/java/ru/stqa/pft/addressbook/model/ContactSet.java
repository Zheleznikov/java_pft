package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ContactSet extends ForwardingSet<ContactData> {

    private Set<ContactData> delegate;

    public ContactSet(ContactSet contactSet) {
        this.delegate = new HashSet<>(contactSet.delegate);
    }

    public ContactSet (Collection<ContactData> contacts) {
        this.delegate = new HashSet<>(contacts);
    }

    public ContactSet() {
        this.delegate = new HashSet<>();
    }

    @Override
    protected Set<ContactData> delegate() {
        return delegate;
    }

    public ContactSet withAdded(ContactData contact) {
        ContactSet contactSet = new ContactSet(this);
        contactSet.add(contact);
        return contactSet;
    }

    public ContactSet without(ContactData contact) {
        ContactSet contactSet = new ContactSet(this);
        contactSet.remove(contact);
        return contactSet;
    }


}
