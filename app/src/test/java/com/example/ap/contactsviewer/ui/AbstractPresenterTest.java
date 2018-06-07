package com.example.ap.contactsviewer.ui;

import com.example.ap.contactsviewer.base.BaseContract;
import com.example.ap.contactsviewer.data.schedular.SchedulerProvider;
import com.example.ap.contactsviewer.data.schedular.TestSchedulerProviderImpl;
import com.example.ap.contactsviewer.data.source.Contact;
import com.example.ap.contactsviewer.data.source.ContactService;
import com.example.ap.contactsviewer.data.source.MockContactStore;

import java.util.List;

public class AbstractPresenterTest<P extends BaseContract.BasePresenter> {

    private P presenter;
    private MockContactStore contactService = MockContactStore.getContactStore();
    private SchedulerProvider schedulerProvider = new TestSchedulerProviderImpl();

    protected P getPresenter() {
        return presenter;
    }

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    protected ContactService getContactService() {
        return contactService;
    }

    protected SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    protected List<Contact> getContactsAsList() {
        return contactService.getContactsAsList();
    }
}