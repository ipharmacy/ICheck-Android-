package test.test.icheck.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import test.test.icheck.R;
import test.test.icheck.ProductDetailActivity;
import test.test.icheck.entity.Product;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.MyViewHolder> implements Filterable {
    private List<Product> dataSet;
    private List<Product> dataSetFull;
    private Context context;

    @Override
    public Filter getFilter() {
        return productFiltre;
    }
    private Filter productFiltre = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filtredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtredList.addAll(dataSetFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product item : dataSetFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filtredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataSet.clear();
            dataSet.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView prodcutName,productRate;
        ImageView productImage,productBrand;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.prodcutName = (TextView) itemView.findViewById(R.id.textView8);
            this.productRate = (TextView) itemView.findViewById(R.id.id_rateproducts);
            this.productImage = (ImageView) itemView.findViewById(R.id.imageView6);
            this.productBrand = (ImageView) itemView.findViewById(R.id.imageView7);
        }
    }

    public AllProductsAdapter(List<Product> data, Context context) {
        this.dataSet = data;
        this.context = context;
        dataSetFull = new ArrayList<>(data);
    }

    @Override
    public AllProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rc_products, parent, false);
        AllProductsAdapter.MyViewHolder myViewHolder = new AllProductsAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final AllProductsAdapter.MyViewHolder holder, final int listPosition) {

        TextView prodcutName = holder.prodcutName;
        TextView productRate = holder.productRate;
        final ImageView productImage = holder.productImage;
        final ImageView productBrand = holder.productBrand;

        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/products/";
        String fullPath = pathImage+dataSet.get(listPosition).getImages().get(0);
        prodcutName.setText(dataSet.get(listPosition).getName());
        productRate.setText(Double.toString(dataSet.get(listPosition).getRate()));
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
