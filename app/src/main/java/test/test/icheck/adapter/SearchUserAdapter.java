package test.test.icheck.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import test.test.icheck.ProfilUserActivity;
import test.test.icheck.R;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.reviews;
import test.test.icheck.refreshDetails;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.MyViewHolder> implements Filterable {
    private ArrayList<Customer> dataSet;
    private ArrayList<Customer> dataSetFull;
    private Context context;

    @Override
    public Filter getFilter() {
        return userFiltre;
    }

    private Filter userFiltre = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Customer> filtredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtredList.addAll(dataSetFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Customer item : dataSetFull) {
                    String concat = item.getFirstName() + item.getLastName();
                    if (concat.toLowerCase().contains(filterPattern)) {
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
            dataSet.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            ImageView image;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.name = (TextView) itemView.findViewById(R.id.id_search_name);
                this.image = (ImageView) itemView.findViewById(R.id.id_search_image);

            }
        }

        public SearchUserAdapter(ArrayList<Customer> data, Context context) {
            this.dataSet = data;
            this.context = context;
              dataSetFull = new ArrayList<>(data);
        }

        @Override
        public SearchUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_search_cv, parent, false);


            SearchUserAdapter.MyViewHolder myViewHolder = new SearchUserAdapter.MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final SearchUserAdapter.MyViewHolder holder, final int listPosition) {
            String pathImage = "https://polar-peak-71928.herokuapp.com/uploads/users/";
            ImageView image = holder.image;
            TextView name = holder.name;
       /* if (dataSet.get(listPosition).getUser() != null) {
            String fullPath = pathImage+dataSet.get(listPosition).getUser().getAvatar();
            ImageView image = holder.image;
            Glide.with(context).load(fullPath).into(image);
        }*/

            String firstName = dataSet.get(listPosition).getFirstName();
            String lastName = dataSet.get(listPosition).getLastName();
            name.setText(firstName + " " + lastName);
            String fullPath = pathImage + dataSet.get(listPosition).getAvatar();
            Glide.with(context).load(fullPath).into(image);
            //image.setImageResource(dataSet.get(listPosition).getImage());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProfilUserActivity.class);
                    intent.putExtra("id",dataSet.get(listPosition).getId());
                    intent.putExtra("firstName",dataSet.get(listPosition).getFirstName());
                    intent.putExtra("lastName",dataSet.get(listPosition).getLastName());
                    intent.putExtra("avatar",dataSet.get(listPosition).getAvatar());
                    intent.putExtra("email",dataSet.get(listPosition).getEmail());
                    intent.putExtra("phone",dataSet.get(listPosition).getPhone());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {

            return dataSet.size();
        }
    }
