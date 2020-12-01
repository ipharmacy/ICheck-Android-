package test.test.icheck.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import test.test.icheck.R;
//import test.test.icheck.details;
import test.test.icheck.entity.product;

public class productAdapter extends RecyclerView.Adapter<productAdapter.MyViewHolder> {
    private ArrayList<product> dataSet;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView prodcutName,productRate,productAddress,productAvailable;
        ImageView productImage,productBrand;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.prodcutName = (TextView) itemView.findViewById(R.id.id_productName);
            this.productRate = (TextView) itemView.findViewById(R.id.id_productRate);
            this.productAddress = (TextView) itemView.findViewById(R.id.id_productAddress);
            this.productAvailable = (TextView) itemView.findViewById(R.id.id_produitAvailable);
            this.productImage = (ImageView) itemView.findViewById(R.id.id_imageProduct);
            this.productBrand = (ImageView) itemView.findViewById(R.id.id_productBrand);
        }
    }

    public productAdapter(ArrayList<product> data,Context context) {
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_cardview, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView prodcutName = holder.prodcutName;
        TextView productRate = holder.productRate;
        TextView productAddress = holder.productAddress;
        TextView productAvailable = holder.productAvailable;
        final ImageView productImage = holder.productImage;
        final ImageView productBrand = holder.productBrand;

        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/products/";
        String fullPath = pathImage+dataSet.get(listPosition).getImages().get(0);

        prodcutName.setText(dataSet.get(listPosition).getName());
        productRate.setText(dataSet.get(listPosition).getRate());
        productAddress.setText(dataSet.get(listPosition).getAddress());
        productAvailable.setText(dataSet.get(listPosition).getAvailable());


        ///  IMAGE

        Glide.with(context).load(fullPath).into(productImage);

      //  Picasso.with(context).load(pathImage+dataSet.get(listPosition).getImages().get(0)).into(productImage);

      //  Glide.with(view.getContext()).load(pathImage+dataSet.get(listPosition).getImages().get(0)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(productBrand);
       // productImage.setImageResource(dataSet.get(listPosition).getProductImage());
        //productBrand.setImageResource(dataSet.get(listPosition).getBrandImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(v.getContext(), details.class);
                //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(),productImage, ViewCompat.getTransitionName(productImage));
                //v.getContext().startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
