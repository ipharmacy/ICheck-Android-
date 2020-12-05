package test.test.icheck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import test.test.icheck.R;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private ArrayList<String> dataSet;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.productPhoto = (ImageView) itemView.findViewById(R.id.id_Produit_Image);
        }
    }

    public PhotoAdapter(ArrayList<String> data, Context context) {
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public PhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_photodetailscard, parent, false);
        PhotoAdapter.MyViewHolder myViewHolder = new PhotoAdapter.MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final PhotoAdapter.MyViewHolder holder, final int listPosition) {
        ImageView productPhoto = holder.productPhoto;
        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/products/";
        String fullPath = pathImage+dataSet.get(listPosition);
        Glide.with(context).load(fullPath).into(productPhoto);
        //productPhoto.setImageResource(dataSet.get(listPosition).getName());
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
