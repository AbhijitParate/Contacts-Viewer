package com.example.ap.contactsviewer.ui.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ap.contactsviewer.R;
import com.example.ap.contactsviewer.data.schedular.SchedulerInjector;
import com.example.ap.contactsviewer.data.source.Contact;
import com.example.ap.contactsviewer.data.source.ContactStore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditContactActivity extends AppCompatActivity
        implements AddEditContract.View {

    public static final String ID_CONTACTS = "_contact_object";
    public static final String LAUNCH_MODE = "_launch_mode";
    public static final String LAUNCH_MODE_ADD = "_launch_mode_add";
    public static final String LAUNCH_MODE_EDIT = "_launch_mode_edit";

    @BindView(R.id.etFirstName)
    EditText etFirstName;

    @BindView(R.id.etLastName)
    EditText etLastName;

    @BindView(R.id.etPhone)
    EditText etPhone;

    private String launchMode;

    private AddEditContract.Presenter presenter;

    public static Intent getLauncherIntent(Activity activity) {
        Intent intent = new Intent(activity, AddEditContactActivity.class);
        intent.putExtra(LAUNCH_MODE, LAUNCH_MODE_ADD);
        return intent;
    }

    public static Intent getLauncherIntent(Activity activity, Contact contact) {
        Intent intent = new Intent(activity, AddEditContactActivity.class);
        intent.putExtra(ID_CONTACTS, contact);
        intent.putExtra(LAUNCH_MODE, LAUNCH_MODE_EDIT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        presenter = new AddEditPresenter(this, ContactStore.getContactStore(), SchedulerInjector.getScheduler());

        launchMode = getIntent().getStringExtra(LAUNCH_MODE);
        if (LAUNCH_MODE_EDIT.equals(launchMode)) {
            Contact contact = getIntent().getParcelableExtra(ID_CONTACTS);
            presenter.onEditContact(contact);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (LAUNCH_MODE_EDIT.equals(launchMode))
            menu.findItem(R.id.action_delete).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            presenter.onSaveContactClicked();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            presenter.onDeleteContactClicked();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    @Override
    public void onError(int stringId) {
        Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getFirstName() {
        return etFirstName.getText().toString();
    }

    @Override
    public void setFirstName(String firstName) {
        etFirstName.setText(firstName);
    }

    @Override
    public String getLastName() {
        return etLastName.getText().toString();
    }

    @Override
    public void setLastName(String lastName) {
        etLastName.setText(lastName);
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        etPhone.setText(phoneNumber);
    }

    @Override
    public boolean isValidPhoneNumber() {
        String phoneNumber = etPhone.getText().toString();
        return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
    }

    @Override
    public void setInvalidPhoneNumber() {
        etPhone.setError(getString(R.string.error_invalid_phone_number));
    }

    @Override
    public void exitActivity() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void contactSaved() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void contactDeleted() {
        Toast.makeText(this, R.string.message_contact_deleted, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}