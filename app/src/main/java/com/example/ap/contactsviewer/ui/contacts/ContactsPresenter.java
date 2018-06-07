package com.example.ap.contactsviewer.ui.contacts;

import com.example.ap.contactsviewer.base.BasePresenter;
import com.example.ap.contactsviewer.data.schedular.SchedulerProvider;
import com.example.ap.contactsviewer.data.source.Contact;
import com.example.ap.contactsviewer.data.source.ContactService;

import java.util.List;

import io.reactivex.observers.DisposableMaybeObserver;

class ContactsPresenter extends BasePresenter<ContactsContract.View> implements ContactsContract.Presenter {

    ContactsPresenter(ContactsContract.View view, ContactService contactService, SchedulerProvider schedulerProvider) {
        super(view, contactService, schedulerProvider);
    }

    @Override
    public void attach(ContactsContract.View view) {
        subscribe();
        getContacts();
    }

    @Override
    public void detach() {
        unsubscribe();
    }

    @Override
    public void onAddContactClicked() {
        getView().startAddContactsActivity();
    }

    @Override
    public void onContactsUpdated() {
        getContacts();
    }

    @Override
    public void onEditContactClicked(Contact contact) {
        getView().showEditContactActivity(contact);
    }

    private void getContacts() {
        addToDisposable(
                getContactService().getContacts(),
                new DisposableMaybeObserver<List<Contact>>() {
                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        getView().setContacts(contacts);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );
    }
}
