package com.example.ap.contactsviewer.data.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

public class MockContactStore implements ContactService {

    private static MockContactStore CONTACT_STORE;

    private final List<Contact> contacts;

    private MockContactStore() {
        contacts = new ArrayList<>();
        addMockContacts();
    }

    private void addMockContacts() {
        addMockContact(new Contact.Builder().setFirstName("Emily").setLastName("Eaton").setPhoneNumber("+1 415 867 9237").build());
        addMockContact(new Contact.Builder().setFirstName("Dakota").setLastName("Watson").setPhoneNumber("+1 415 867 9237").build());
        addMockContact(new Contact.Builder().setFirstName("Alice").setLastName("Johnson").setPhoneNumber("+1 415 867 9237").build());
        addMockContact(new Contact.Builder().setFirstName("Corey").setLastName("Anderson").setPhoneNumber("+1 415 867 9237").build());
        addMockContact(new Contact.Builder().setFirstName("Bob").setLastName("Smith").setPhoneNumber("+1 415 867 9237").build());
        addMockContact(new Contact.Builder().setFirstName("Brook").setLastName("Evans").setPhoneNumber("+1 415 867 9237").build());
    }

    private void addMockContact(Contact contact) {
        contact.setId(contacts.size());
        contacts.add(contacts.size(), contact);
    }

    public static MockContactStore getContactStore() {
        return CONTACT_STORE != null ? CONTACT_STORE : (CONTACT_STORE = new MockContactStore());
    }

    @Override
    public Maybe<List<Contact>> getContacts() {
        return Maybe.create(new MaybeOnSubscribe<List<Contact>>() {
            @Override
            public void subscribe(MaybeEmitter<List<Contact>> emitter) {
                Collections.sort(contacts);
                emitter.onSuccess(contacts);
            }
        });
    }

    @Override
    public Completable setContact(int id, final Contact contact) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getId() == contact.getId()) {
                        contacts.set(i, contact);
                        break;
                    }
                }
                contacts.add(contacts.size(), contact);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Completable addContact(final Contact contact) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                contact.setId(contacts.size());
                contacts.add(contacts.size(), contact);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Completable deleteContact(final Contact contact) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                contacts.remove(contact);
                emitter.onComplete();
            }
        });
    }

    public List<Contact> getContactsAsList() {
        return contacts;
    }
}
