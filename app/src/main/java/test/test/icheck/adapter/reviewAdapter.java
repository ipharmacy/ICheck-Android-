package test.test.icheck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import test.test.icheck.R;
import test.test.icheck.entity.reviews;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.MyViewHolder> {
    private ArrayList<reviews> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,rate,message;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.id_reviewName);
            this.rate = (TextView) itemView.findViewById(R.id.id_reviewRate);
            this.message = (TextView) itemView.findViewById(R.id.id_reviewMessage);
            this.image = (ImageView) itemView.findViewById(R.id.id_imageReview);

        }
    }

    public reviewAdapter(ArrayList<reviews> data) {
        this.dataSet = data;
    }

    @Override
    public reviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_reviewscardview, parent, false);


        reviewAdapter.MyViewHolder myViewHolder = new reviewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final reviewAdapter.MyViewHolder holder, final int listPosition) {

        TextView name = holder.name;
        TextView rate = holder.rate;
        TextView message = holder.message;
        ImageView image = holder.image;
        String rate2 = Integer.toString(dataSet.get(listPosition).getRate());
        name.setText(dataSet.get(listPosition).getName());
        rate.setText(rate2);
        message.setText(dataSet.get(listPosition).getReview());
        image.setImageResource(dataSet.get(listPosition).getImage());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
