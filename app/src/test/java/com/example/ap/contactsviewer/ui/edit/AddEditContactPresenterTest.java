package com.example.ap.contactsviewer.ui.edit;

import com.example.ap.contactsviewer.R;
import com.example.ap.contactsviewer.data.source.Contact;
import com.example.ap.contactsviewer.ui.AbstractPresenterTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddEditContactPresenterTest extends AbstractPresenterTest<AddEditContract.Presenter> {

    @Mock AddEditContract.View view;

    @Before
    public void beforeTest() {
        // Instantiate the presenter with mocked view and fake contact store
       setPresenter(new AddEditPresenter(view, getContactService(), getSchedulerProvider()));
    }

    /**
     * When user clicks on save button but the phone number is invalid
     */
    @Test
    public void test_onSaveClicked_Failure() {
        when(view.isValidPhoneNumber()).thenReturn(false);

        getPresenter().onSaveContactClicked();
        verify(view).setInvalidPhoneNumber();
        verify(view).onError(R.string.error_invalid_phone_number);
    }

    /**
     * When user clicks save button to create new contact
     */
    @Test
    public void test_onSaveClicked_Success() {
        when(view.isValidPhoneNumber()).thenReturn(true);
        when(view.getFirstName()).thenReturn("William");
        when(view.getLastName()).thenReturn("Campos");
        when(view.getPhoneNumber()).thenReturn("+1 750 091 3259");

        getPresenter().onSaveContactClicked();
        verify(view).getFirstName();
        verify(view).getLastName();
        verify(view).getPhoneNumber();
        verify(view).contactSaved();
    }

    /**
     * When user clicks save button but doesn't edit anything
     */
    @Test
    public void test_onEditContact_NoChange() {
        List<Contact> contacts = getContactsAsList();
        Contact contact = contacts.get(new Random().nextInt(contacts.size()));
        when(view.getFirstName()).thenReturn(contact.getFirstName());
        when(view.getLastName()).thenReturn(contact.getLastName());
        when(view.getPhoneNumber()).thenReturn(contact.getPhoneNumber());

        getPresenter().onEditContact(contact);
        getPresenter().onSaveContactClicked();

        verify(view).getFirstName();
        verify(view).getLastName();
        verify(view).getPhoneNumber();

        verify(view).exitActivity();
    }

    /**
     * When updates contact by changing the phone number
     */
    @Test
    public void test_onEditContact_PhoneChanged() {
        when(view.isValidPhoneNumber()).thenReturn(true);

        Contact contact = getContactsAsList().get(new Random().nextInt(getContactsAsList().size()));
        when(view.getFirstName()).thenReturn(contact.getFirstName());
        when(view.getLastName()).thenReturn(contact.getLastName());
        String newRandomPhoneNumber = getRandomPhoneNumber();
        when(view.getPhoneNumber()).thenReturn(newRandomPhoneNumber);

        getPresenter().onSaveContactClicked();

        verify(view).contactSaved();
    }

    /**
     * When user clicks on delete contact
     */
    @Test
    public void test_onDeleteContact() {
        List<Contact> contacts = getContactsAsList();
        Contact contact = contacts.get(new Random().nextInt(contacts.size() - 1));
        getPresenter().onEditContact(contact);
        getPresenter().onDeleteContactClicked();
        verify(view).contactDeleted();
        Assert.assertFalse("Contact not removed", contacts.contains(contact));
    }

    private String getRandomPhoneNumber() {
        StringBuilder sb = new StringBuilder("+1");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
