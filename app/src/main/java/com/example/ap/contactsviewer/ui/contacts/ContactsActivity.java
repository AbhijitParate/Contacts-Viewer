package com.example.ap.contactsviewer.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ap.contactsviewer.R;
import com.example.ap.contactsviewer.data.schedular.SchedulerInjector;
import com.example.ap.contactsviewer.data.source.Contact;
import com.example.ap.contactsviewer.data.source.ContactStore;
import com.example.ap.contactsviewer.ui.edit.AddEditContactActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity
        implements ContactsContract.View, ContactsAdapter.OnContactClickListener {

    private static final int REQUEST_CODE_ADD_CONTACT = 900;
    private static final int REQUEST_CODE_EDIT_CONTACT = 902;

    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;

    private ContactsContract.Presenter presenter;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        // create adapter with empty list of contacts
        adapter = new ContactsAdapter(this);
        rvContacts.setAdapter(adapter);
        // create instance of presenter
        presenter = new ContactsPresenter(this, ContactStore.getContactStore(), SchedulerInjector.getScheduler());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            presenter.onAddContactClicked();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == REQUEST_CODE_ADD_CONTACT || requestCode == REQUEST_CODE_EDIT_CONTACT)) {
            presenter.onContactsUpdated();
        }
    }

    @Override
    public void onError(int stringId) {
        Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startAddContactsActivity() {
        startActivityForResult(AddEditContactActivity.getLauncherIntent(this), REQUEST_CODE_ADD_CONTACT);
    }

    @Override
    public void setContacts(List<Contact> contacts) {
        // updates / adds contacts to RecyclerView Adapter
        adapter.updateContacts(contacts);
    }

    @Override
    public void showEditContactActivity(Contact contact) {
        startActivityForResult(AddEditContactActivity.getLauncherIntent(this, contact), REQUEST_CODE_EDIT_CONTACT);
    }

    @Override
    public void onContactClicked(Contact contact) {
        //Toast.makeText(this, contact.getFirstName() + " " + contact.getLastName() + " clicked.", Toast.LENGTH_SHORT).show();
        // single click on contact item from RecyclerView lets you edit the contact
        presenter.onEditContactClicked(contact);
    }
}
