package test.test.icheck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import test.test.icheck.R;
import test.test.icheck.entity.product;

public class productAdapter extends RecyclerView.Adapter<productAdapter.MyViewHolder> {
    private ArrayList<product> dataSet;

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

    public productAdapter(ArrayList<product> data) {
        this.dataSet = data;
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
        ImageView productImage = holder.productImage;
        ImageView productBrand = holder.productBrand;
        String rate = Integer.toString(dataSet.get(listPosition).getRate());
        prodcutName.setText(dataSet.get(listPosition).getName());
        productRate.setText(rate);
        productAddress.setText(dataSet.get(listPosition).getAddress());
        productAvailable.setText(dataSet.get(listPosition).getAvailable());
        productImage.setImageResource(dataSet.get(listPosition).getProductImage());
        productBrand.setImageResource(dataSet.get(listPosition).getBrandImage());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
