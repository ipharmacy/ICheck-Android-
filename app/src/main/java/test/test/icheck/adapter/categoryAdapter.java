package test.test.icheck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import test.test.icheck.R;
import test.test.icheck.entity.friends;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyViewHolder> {
    private ArrayList<String> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryText;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.categoryText = (TextView) itemView.findViewById(R.id.id_cv_categoryname);
        }
    }

    public categoryAdapter(ArrayList<String> data) {
        this.dataSet = data;
    }

    @Override
    public categoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rc_categories, parent, false);


        categoryAdapter.MyViewHolder myViewHolder = new categoryAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final categoryAdapter.MyViewHolder holder, final int listPosition) {
        TextView categoryText = holder.categoryText;
      //  friendImage.setImageResource(dataSet.get(listPosition).getFriendImage());
        String category =dataSet.get(listPosition);
        categoryText.setText(category);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
