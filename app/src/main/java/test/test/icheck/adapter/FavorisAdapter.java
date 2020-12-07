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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import test.test.icheck.ProductDetailActivity;
import test.test.icheck.R;
import test.test.icheck.entity.Product;

public class FavorisAdapter extends RecyclerView.Adapter<FavorisAdapter.MyViewHolder> {
    private List<Product> dataSet;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView prodcutName,productRate,productAddress,productAvailable;
        ImageView productImage,productBrand;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.prodcutName = (TextView) itemView.findViewById(R.id.id_favoris_productname);
            this.productRate = (TextView) itemView.findViewById(R.id.id_rate_favoris);
            this.productAddress = (TextView) itemView.findViewById(R.id.id_favoris_description);
            this.productAvailable = (TextView) itemView.findViewById(R.id.id_favoris_available);
            this.productImage = (ImageView) itemView.findViewById(R.id.imageView);
            this.productBrand = (ImageView) itemView.findViewById(R.id.id_favoris_logobrand);
        }
    }

    public FavorisAdapter(List<Product> data, Context context) {
        this.dataSet = data;
        this.context = context;
    }

    @Override
    public FavorisAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rv_favoris, parent, false);


        FavorisAdapter.MyViewHolder myViewHolder = new FavorisAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FavorisAdapter.MyViewHolder holder, final int listPosition) {

        TextView prodcutName = holder.prodcutName;
        TextView productRate = holder.productRate;
        TextView productAddress = holder.productAddress;
        TextView productAvailable = holder.productAvailable;
        final ImageView productImage = holder.productImage;
        final ImageView productBrand = holder.productBrand;

        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/products/";
        String fullPath = pathImage+dataSet.get(listPosition).getImages().get(0);
        prodcutName.setText(dataSet.get(listPosition).getName());
        productRate.setText(Double.toString(dataSet.get(listPosition).getRate()));
        productAddress.setText(dataSet.get(listPosition).getAddress());
        productAvailable.setText(dataSet.get(listPosition).getAvailable());

        Glide.with(context).load(fullPath).into(productImage);
        Glide.with(context).load("https://polar-peak-71928.herokuapp.com/uploads/brands/"+dataSet.get(listPosition).getBrand()+".jpg").into(productBrand);
        // productImage.setImageResource(dataSet.get(listPosition).getProductImage());
        //productBrand.setImageResource(dataSet.get(listPosition).getBrandImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId",dataSet.get(listPosition).getId());
                // intent.putExtra("product", (Serializable) dataSet.get(listPosition));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
