package test.test.icheck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import test.test.icheck.R;
import test.test.icheck.entity.photoProduct;

public class photoAdapter extends RecyclerView.Adapter<photoAdapter.MyViewHolder> {
    private ArrayList<photoProduct> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.productPhoto = (ImageView) itemView.findViewById(R.id.id_Produit_Image);
        }
    }

    public photoAdapter(ArrayList<photoProduct> data) {
        this.dataSet = data;
    }

    @Override
    public photoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_photodetailscard, parent, false);
        photoAdapter.MyViewHolder myViewHolder = new photoAdapter.MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final photoAdapter.MyViewHolder holder, final int listPosition) {
        ImageView productPhoto = holder.productPhoto;
        //productPhoto.setImageResource(dataSet.get(listPosition).getName());
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
