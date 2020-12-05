package test.test.icheck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import test.test.icheck.R;
import test.test.icheck.entity.friends;


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder>  {
    private ArrayList<friends> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView friendImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.friendImage = (ImageView) itemView.findViewById(R.id.id_friendImage);
        }
    }

    public FriendsAdapter(ArrayList<friends> data) {
        this.dataSet = data;
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
        friendImage.setImageResource(dataSet.get(listPosition).getFriendImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

