package com.example.ap.contactsviewer.ui.contacts;

import com.example.ap.contactsviewer.data.source.Contact;
import com.example.ap.contactsviewer.ui.AbstractPresenterTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactsPresenterTest extends AbstractPresenterTest<ContactsContract.Presenter> {

    private static final String TAG = "ContactsPresenterTest";

    @Mock ContactsContract.View view;

    @Before
    public void beforeTest(){
        // Instantiate the presenter with mocked view and fake contact store
        setPresenter(new ContactsPresenter(view, getContactService(), getSchedulerProvider()));
    }

    /**
     * When user clicks on Add option from options menu
     */
    @Test
    public void test_onAddContactClicked() {
        getPresenter().onAddContactClicked();
        verify(view).startAddContactsActivity();
    }

    /**
     * When user clicks on one of the contacts from the recyclerView
     */
    @Test
    public void test_onContactClicked() {
        Contact contact = getContactsAsList().get(new Random().nextInt(getContactsAsList().size()));
        getPresenter().onEditContactClicked(contact);
        verify(view).showEditContactActivity(contact);
    }

    /**
     * When user returns from AddEdit activity either by deleting  or by updating the contact
     */
    @Test
    public void test_onContactsUpdated() {
        getPresenter().onContactsUpdated();
        verify(view, atLeastOnce()).setContacts(getContactsAsList());
    }
}