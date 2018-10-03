package com.typicalgeek.chatsample;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Cursor res;
    DatabaseHelper databaseHelper;
    EditText etMessage;
    private static final String self = "dev";
    private static final String contact = "geek";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.rvContacts);
        databaseHelper = new DatabaseHelper(this);

        refresh();
    }

    private void refresh(){
        recyclerView.setHasFixedSize(true);
        ContactsAdapter adapter = new ContactsAdapter(dbGetDistinctItems());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
    }

    private Contact[] dbGetDistinctItems() {
        return new Contact[] {
                new Contact(189956, contact, R.drawable.user+""),
                new Contact(189346, "contact", R.drawable.user+""),
        };
    }
}

class Message{
    private int messageID;
    private String messageContent;
    private String messageSender;
    private String messageRecipient;
    private String messageDate;

    Message(int messageID, String messageContent, String messageSender, String messageRecipient, String messageDate) {
        this.messageID = messageID;
        this.messageContent = messageContent;
        this.messageSender = messageSender;
        this.messageRecipient = messageRecipient;
        this.messageDate = messageDate;
    }

    Message(String messageContent, String messageSender, String messageRecipient, String messageDate) {
        this.messageContent = messageContent;
        this.messageSender = messageSender;
        this.messageRecipient = messageRecipient;
        this.messageDate = messageDate;
    }

    public int getMessageID() {
        return messageID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public String getMessageRecipient() {
        return messageRecipient;
    }

    public String getMessageDate() {
        return messageDate;
    }
}

class Contact{
    private int contactID;
    private String contactUsername;
    private String contactPhoto;

    Contact(int contactID, String contactUsername, String contactPhoto) {
        this.contactID = contactID;
        this.contactUsername = contactUsername;
        this.contactPhoto = contactPhoto;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactUsername() {
        return contactUsername;
    }

    public String getContactPhoto() {
        return contactPhoto;
    }
}

