package test.test.icheck.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import test.test.icheck.R;
import test.test.icheck.entity.MessageChat;

public class MessageAdapter extends RecyclerView.Adapter {
    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;
    private static final int TYPE_IMAGE_SENT = 2;
    private static final int TYPE_IMAGE_RECEIVED = 3;

    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();
    private String connectedUser;
    private String imageUser;
    Context context;
    private ArrayList<MessageChat> messageChats;
    public MessageAdapter (LayoutInflater inflater, String connectedUser, ArrayList<MessageChat> messageChats, String imageUser, Context context) {
        this.inflater = inflater;
        this.connectedUser = connectedUser;
        this.messageChats = messageChats;
        this.imageUser = imageUser;
        this.context=context;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageTxt;
        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageTxt = itemView.findViewById(R.id.messageSelf);
        }
    }
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, messageTxt;
        ImageView imageSender;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);

            //nameTxt = itemView.findViewById(R.id.nameSender);
            messageTxt = itemView.findViewById(R.id.messageSender);
            imageSender = itemView.findViewById(R.id.imageSender);
        }
    }

    @Override
    public int getItemViewType(int position) {
     JSONObject message = messages.get(position);
        try {
            if (message.getString("senderId").equals(connectedUser)){
                return TYPE_MESSAGE_SENT;
            }else {
                return TYPE_MESSAGE_RECEIVED;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return -1;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.chat_self, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.chat_sender, parent, false);
                return new ReceivedMessageHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JSONObject message = messages.get(position);
        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/users/";
        System.out.println("messages : "+messages+" Position : "+position);
       try {
           if (message.getString("senderId").equals(connectedUser)){
               SentMessageHolder messageHolder = (SentMessageHolder) holder;
               messageHolder.messageTxt.setText(message.getString("message"));
           }else {
               ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
               //messageHolder.nameTxt.setText(message.getString("name"));
               Glide.with(context).load(pathImage+imageUser).into(messageHolder.imageSender);
               messageHolder.messageTxt.setText(message.getString("message"));
           }
       }catch (JSONException e){
           e.printStackTrace();
       }
    }
    private Bitmap getBitmapFromString(String image) {
        byte[] bytes = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    @Override
    public int getItemCount() {
        //return messages.size();
        return messages.size();
    }
    public void addItem (JSONObject jsonObject) {
        messages.add(jsonObject);
        notifyDataSetChanged();
    }
}
