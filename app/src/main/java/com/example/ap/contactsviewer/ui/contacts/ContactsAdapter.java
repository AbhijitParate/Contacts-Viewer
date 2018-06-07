package com.example.ap.contactsviewer.ui.contacts;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ap.contactsviewer.R;
import com.example.ap.contactsviewer.data.source.Contact;
import com.example.ap.contactsviewer.view.ContactInitialsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsHolder> {

    private List<Contact> contactList;
    private OnContactClickListener contactClickListener;

    ContactsAdapter(OnContactClickListener clickListener) {
        contactList = new ArrayList<>();
        this.contactClickListener = clickListener;
    }

    @NonNull
    @Override
    public ContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_contact, parent, false);
        return new ContactsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsHolder holder, int position) {
        Contact contact = contactList.get(position);
        String s = "";
        if (!TextUtils.isEmpty(contact.getFirstName()))
            s += contact.getFirstName().charAt(0);
        if (!TextUtils.isEmpty(contact.getLastName()))
            s += contact.getLastName().charAt(0);

        holder.initials.setText(s);
        holder.initials.setBackgroundColor(getRandomColor());

        if (!TextUtils.isEmpty(contact.getFirstName()) || !TextUtils.isEmpty(contact.getLastName())) {
            holder.name.setText(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
            holder.phone.setText(contact.getPhoneNumber());
        } else {
            holder.name.setText(contact.getPhoneNumber());
            holder.phone.setText("");
        }
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void updateContacts(List<Contact> contacts) {
        if (contacts != null && !contacts.isEmpty()) {
            contactList.clear();
            contactList.addAll(contacts);
            notifyDataSetChanged();
        }
    }

    interface OnContactClickListener {
        void onContactClicked(Contact contact);
    }

    class ContactsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvInitials)
        ContactInitialsView initials;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.phone)
        TextView phone;

        ContactsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.layout_contact)
        void onContactClick() {
            contactClickListener.onContactClicked(contactList.get(getAdapterPosition()));
        }
    }
}
