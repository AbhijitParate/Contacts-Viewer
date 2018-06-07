package com.example.ap.contactsviewer.ui.edit;

import com.example.ap.contactsviewer.base.BaseContract;
import com.example.ap.contactsviewer.data.source.Contact;

interface AddEditContract {
    interface View extends BaseContract.View {
        String getFirstName();
        String getLastName();
        String getPhoneNumber();

        void setFirstName(String firstName);
        void setLastName(String firstName);
        void setPhoneNumber(String firstName);

        boolean isValidPhoneNumber();
        void setInvalidPhoneNumber();

        void exitActivity();
        void contactSaved();
        void contactDeleted();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void onEditContact(Contact contact);
        void onSaveContactClicked();
        void onDeleteContactClicked();
    }
}
