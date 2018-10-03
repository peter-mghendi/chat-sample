package com.typicalgeek.chatsample;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    RecyclerView rvChat;
    EditText etMessage;
    private static final String self = "101010";
    private final DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private int userID;
    private String username, userphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ImageButton btnSend = findViewById(R.id.btnSend);
        rvChat = findViewById(R.id.rvChats);
        etMessage = findViewById(R.id.etMessage);

        Intent i = getIntent();
        userID = i.getIntExtra("userID", 0);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(i.getStringExtra("username"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String msg = etMessage.getText().toString().trim();
                        if (!msg.isEmpty()) {
                            if (dbInsert(buildMessage(msg, self, userID))) {
                                etMessage.setText("");
                                refresh();
                            } else {
                                Toast.makeText(ChatActivity.this, "Message not sent", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            etMessage.setText("");
                            Toast.makeText(ChatActivity.this, "Type a message", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ChatActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            etMessage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etMessage.getText().toString().trim().isEmpty()) btnSend.setVisibility(View.GONE);
                    else btnSend.setVisibility(View.VISIBLE);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            refresh();
    }

    private void refresh(){
        rvChat.setHasFixedSize(true);
        ChatsAdapter adapter = new ChatsAdapter(dbGetItems());
        rvChat.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvChat.setLayoutManager(llm);
        if (adapter.getItemCount() > 0) rvChat
                .smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private Message buildMessage(String content, String sender, int recipient) {
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm",
                Locale.getDefault()).format(new Date());
        String rec = String.valueOf(recipient);
        return new Message(content, sender, rec, date);
    }

    private boolean dbInsert(Message message) {
        boolean success = databaseHelper.insertMessage(message);
        databaseHelper.insertMessage(buildMessage("Ssup", userID+"", 101010)); // REPLY, FOR TESTING PURPOSES
        return success;
    }
    private Message[] dbGetItems() {
        Cursor res = databaseHelper.getAllData(userID);
        Message[] messages = new Message[res.getCount()];
        if (res.getCount() > 0) {
            int i = 0;
            while (res.moveToNext()) {
                messages[i] = new Message(
                        res.getInt(0),
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4));
                i++;
            }
        }
        return messages;
    }
}
