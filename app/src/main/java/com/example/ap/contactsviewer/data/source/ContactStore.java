package com.example.ap.contactsviewer.data.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

/**
 * Implementation of {@link ContactService}
 */
public class ContactStore implements ContactService {

    private static ContactStore CONTACT_STORE;

    private List<Contact> contacts;
    private static int idIndex = 0;

    private ContactStore() {
        contacts = new ArrayList<>();
        addInitialContacts();
    }

    private void addInitialContacts() {
        addFakeContact(new Contact.Builder().setFirstName("Emily").setLastName("Eaton").setPhoneNumber("+1 415 867 9237").build());
        addFakeContact(new Contact.Builder().setFirstName("Dakota").setLastName("Watson").setPhoneNumber("+1 415 867 9237").build());
        addFakeContact(new Contact.Builder().setFirstName("Alice").setLastName("Johnson").setPhoneNumber("+1 415 867 9237").build());
        addFakeContact(new Contact.Builder().setFirstName("Corey").setLastName("Anderson").setPhoneNumber("+1 415 867 9237").build());
        addFakeContact(new Contact.Builder().setFirstName("Bob").setLastName("Smith").setPhoneNumber("+1 415 867 9237").build());
        addFakeContact(new Contact.Builder().setFirstName("Brook").setLastName("Evans").setPhoneNumber("+1 415 867 9237").build());
    }

    private void addFakeContact(Contact contact) {
        contact.setId(contacts.size());
        contacts.add(contacts.size(), contact);
    }

    public static ContactService getContactStore() {
        return CONTACT_STORE != null ? CONTACT_STORE : (CONTACT_STORE = new ContactStore());
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
    public Completable setContact(final int id, final Contact contact) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                Iterator<Contact> iterator = contacts.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getId() == id) {
                        iterator.remove();
                        break;
                    }
                }
                contact.setId(id);
                contacts.add(contact);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Completable addContact(final Contact contact) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                contact.setId(++idIndex);
                contacts.add(contact);
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
}
