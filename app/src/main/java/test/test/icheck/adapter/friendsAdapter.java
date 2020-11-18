package test.test.icheck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import test.test.icheck.R;
import test.test.icheck.entity.friends;


public class friendsAdapter extends RecyclerView.Adapter<friendsAdapter.MyViewHolder>  {
    private ArrayList<friends> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView friendImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.friendImage = (ImageView) itemView.findViewById(R.id.id_friendImage);
        }
    }

    public friendsAdapter(ArrayList<friends> data) {
        this.dataSet = data;
    }

    @Override
    public friendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_friendscardview, parent, false);


        friendsAdapter.MyViewHolder myViewHolder = new friendsAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final friendsAdapter.MyViewHolder holder, final int listPosition) {
        ImageView friendImage = holder.friendImage;
        friendImage.setImageResource(dataSet.get(listPosition).getFriendImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

