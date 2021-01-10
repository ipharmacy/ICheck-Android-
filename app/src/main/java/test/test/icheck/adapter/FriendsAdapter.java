package test.test.icheck.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import test.test.icheck.ChatActivity;
import test.test.icheck.R;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.friends;


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder>  {
    private ArrayList<Customer> dataSet;
    Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView friendImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.friendImage = (ImageView) itemView.findViewById(R.id.id_friendImage);
        }
    }

    public FriendsAdapter(ArrayList<Customer> data,Context context) {
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public FriendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_friendscardview, parent, false);


        FriendsAdapter.MyViewHolder myViewHolder = new FriendsAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FriendsAdapter.MyViewHolder holder, final int listPosition) {
        ImageView friendImage = holder.friendImage;
        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/users/";
        String fullPath = pathImage+dataSet.get(listPosition).getAvatar();
        //friendImage.setImageResource(dataSet.get(listPosition).);
        Glide.with(context).load(fullPath).into(friendImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("senderId",dataSet.get(listPosition).getId());
                intent.putExtra("imageSender",dataSet.get(listPosition).getAvatar());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

