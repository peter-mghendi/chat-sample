package com.typicalgeek.chatsample;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MyViewHolder>{
    private Message[] mMessages;
    private final static String SELF = "101010";
    ChatsAdapter(Message[] messages){
        mMessages = messages;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvMessage, tvDate;
        CardView cardView;
        LinearLayout layoutItemView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvDate);
            layoutItemView = itemView.findViewById(R.id.layoutItemView);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (tvDate.getVisibility() == View.VISIBLE) tvDate.setVisibility(View.GONE);
            else tvDate.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_chat_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Message m = mMessages[i];
        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                holder.layoutItemView.getLayoutParams();

        holder.tvMessage.setText(m.getMessageContent());
        holder.tvDate.setText(m.getMessageDate());
        if (m.getMessageSender().equals(SELF)){
            holder.cardView.setCardBackgroundColor(Color.DKGRAY);
            holder.tvMessage.setTextColor(Color.LTGRAY);
            params.gravity = GravityCompat.END;
        } else {
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
            holder.tvMessage.setTextColor(Color.DKGRAY);
            params.gravity = GravityCompat.START;
        }
        holder.layoutItemView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mMessages.length;
    }
}
