package com.example.ap.contactsviewer.data.source;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface ContactService {
    /**
     * gets the sorted list of contacts from repo
     *
     * @return List of {@link Contact}
     */
    Maybe<List<Contact>> getContacts();

    /**
     * Update contact
     *
     * @param id      contactId
     * @param contact contact object
     */
    Completable setContact(int id, Contact contact);

    /**
     * Adds new contact to repo
     *
     * @param contact contact object
     */
    Completable addContact(Contact contact);

    /**
     * removes contact from repo
     *
     * @param contact contact object
     */
    Completable deleteContact(Contact contact);
}
