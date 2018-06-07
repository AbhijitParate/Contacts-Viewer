package com.example.ap.contactsviewer.ui.contacts;

import com.example.ap.contactsviewer.base.BaseContract;
import com.example.ap.contactsviewer.data.source.Contact;

import java.util.List;

interface ContactsContract {
    interface View extends BaseContract.View {
        void startAddContactsActivity();
        void setContacts(List<Contact> contacts);
        void showEditContactActivity(Contact contact);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void onAddContactClicked();
        void onContactsUpdated();
        void onEditContactClicked(Contact contact);
    }
}
