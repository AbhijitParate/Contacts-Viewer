package com.example.ap.contactsviewer.ui.edit;

import com.example.ap.contactsviewer.R;
import com.example.ap.contactsviewer.base.BasePresenter;
import com.example.ap.contactsviewer.data.schedular.SchedulerProvider;
import com.example.ap.contactsviewer.data.source.Contact;
import com.example.ap.contactsviewer.data.source.ContactService;

import java.util.Objects;

import io.reactivex.observers.DisposableCompletableObserver;

class AddEditPresenter extends BasePresenter<AddEditContract.View> implements AddEditContract.Presenter {

    private Contact contact;

    AddEditPresenter(AddEditContract.View view, ContactService contactService, SchedulerProvider schedulerProvider) {
        super(view, contactService, schedulerProvider);
    }

    @Override
    public void attach(AddEditContract.View editView) {
        subscribe();
    }

    @Override
    public void detach() {
        unsubscribe();
    }

    @Override
    public void onSaveContactClicked() {
        if (contact != null
                && Objects.equals(contact.getFirstName(), getView().getFirstName())
                && Objects.equals(contact.getLastName(), getView().getLastName())
                && Objects.equals(contact.getPhoneNumber(), getView().getPhoneNumber())) {
            getView().exitActivity();
        } else if (getView().isValidPhoneNumber()) {
            Contact contact = new Contact.Builder()
                    .setFirstName(getView().getFirstName())
                    .setLastName(getView().getLastName())
                    .setPhoneNumber(getView().getPhoneNumber())
                    .build();
            if (this.contact == null) {
                addToDisposable(
                        getContactService().addContact(contact),
                        new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                getView().contactSaved();
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().onError(R.string.error_generic_message);
                            }
                        }

                );
            } else {
                addToDisposable(
                        getContactService().setContact(this.contact.getId(), contact),
                        new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                getView().contactSaved();
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().onError(R.string.error_generic_message);
                            }
                        }
                );
            }

        } else {
            getView().setInvalidPhoneNumber();
            getView().onError(R.string.error_invalid_phone_number);
        }
    }

    @Override
    public void onDeleteContactClicked() {
        addToDisposable(
                getContactService().deleteContact(contact),
                new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getView().contactDeleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onError(R.string.error_generic_message);
                    }
                }
        );
    }

    @Override
    public void onEditContact(Contact contact) {
        this.contact = contact;
        getView().setFirstName(contact.getFirstName());
        getView().setLastName(contact.getLastName());
        getView().setPhoneNumber(contact.getPhoneNumber());
    }
}
