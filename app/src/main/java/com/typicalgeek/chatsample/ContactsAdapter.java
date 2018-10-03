package com.typicalgeek.chatsample;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private Contact[] mContacts;

    ContactsAdapter(Contact[] contacts) {
        mContacts = contacts;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvContactName, tvLastMessage;
        ImageView imageContactPhoto;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContactName = itemView.findViewById(R.id.tvContactName);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            imageContactPhoto = itemView.findViewById(R.id.imageContactPhoto);
            itemView.setOnClickListener(this);
            imageContactPhoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Contact c = mContacts[getAdapterPosition()];
            if (v.equals(itemView)){
                v.getContext().startActivity(new Intent(v.getContext(), ChatActivity.class)
                        .putExtra("userID", c.getContactID())
                        .putExtra("username", c.getContactUsername())
                        .putExtra("userPhoto", c.getContactPhoto()));
            } else if (v.equals(imageContactPhoto)){
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View imgView = inflater.inflate(R.layout.layout_contact_image,
                        (ViewGroup) v.getParent(), false);
                ImageView imageView = imgView.findViewById(R.id.contactImageExpanded);
                new AlertDialog.Builder(v.getContext())
                        .setView(imgView)
                        .create().show();
                Drawable photo = v.getContext().getDrawable(Integer.parseInt(c.getContactPhoto()));
                imageView.setImageDrawable(photo);
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_contact_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Contact c = mContacts[i];
        int resID = Integer.parseInt(c.getContactPhoto());
        Drawable drawable = holder.itemView.getContext().getDrawable(resID);
        holder.imageContactPhoto.setImageDrawable(drawable);
        holder.tvContactName.setText(c.getContactUsername());

        Cursor crsr = new DatabaseHelper(holder.itemView.getContext()).getAllData(c.getContactID());
        ArrayList<Message> messageArrayList = new ArrayList<>();
        if (crsr.getCount() > 0) {
            while (crsr.moveToNext()) {
                Message message = new Message(
                        crsr.getInt(0),
                        crsr.getString(1),
                        crsr.getString(2),
                        crsr.getString(3),
                        crsr.getString(4));
                messageArrayList.add(message);
            }
        }
        String lastMessage = messageArrayList.get(messageArrayList.size() - 1).getMessageContent();
        holder.tvLastMessage.setText(lastMessage);
    }


    @Override
    public int getItemCount() {
        return mContacts.length;
    }
}
