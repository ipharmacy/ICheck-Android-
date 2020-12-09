package test.test.icheck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import test.test.icheck.R;
import test.test.icheck.entity.reviews;
import test.test.icheck.refreshDetails;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private ArrayList<reviews> dataSet;
    private Context context;
    private int adapterfinder;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,rate,message,reviewTime;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.id_reviewName);
            this.rate = (TextView) itemView.findViewById(R.id.id_reviewRate);
            this.message = (TextView) itemView.findViewById(R.id.id_reviewMessage);
            this.image = (ImageView) itemView.findViewById(R.id.id_imageReview);
            this.reviewTime=(TextView)itemView.findViewById(R.id.id_createdAtReview);

        }
    }

    public ReviewAdapter(ArrayList<reviews> data, Context context,int adapterfinder) {
        this.dataSet = data;
        this.context = context;
        this.adapterfinder=adapterfinder;
    }
    public void insertData(ArrayList<reviews> insertList){
        refreshDetails refresh = new refreshDetails(dataSet,insertList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(refresh);
        dataSet.addAll(insertList);
        diffResult.dispatchUpdatesTo(this);
    }
    public void updateData(ArrayList<reviews> insertList){
        refreshDetails refresh = new refreshDetails(dataSet,insertList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(refresh);
        dataSet.clear();
        dataSet.addAll(insertList);
        diffResult.dispatchUpdatesTo(this);
    }
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_reviewscardview, parent, false);


        ReviewAdapter.MyViewHolder myViewHolder = new ReviewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder holder, final int listPosition) {
        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/users/";

        if (dataSet.get(listPosition).getUser() != null) {
            String fullPath = pathImage+dataSet.get(listPosition).getUser().getAvatar();
            ImageView image = holder.image;
            Glide.with(context).load(fullPath).into(image);
        }
        TextView name = holder.name;
        TextView rate = holder.rate;
        TextView message = holder.message;
        TextView revTime=holder.reviewTime;
        Date date = dataSet.get(listPosition).getCreatedAt();
        DateFormat shortDateFormatEN = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT, new Locale("EN","en"));
        String rate2 = Double.toString(dataSet.get(listPosition).getRate());
        String firstName =dataSet.get(listPosition).getUser().getFirstName();
        String lastName = dataSet.get(listPosition).getUser().getLastName();
        revTime.setText(shortDateFormatEN.format(date));
        name.setText(firstName+" "+lastName);
        rate.setText(rate2);
        message.setText(dataSet.get(listPosition).getReview());
        //image.setImageResource(dataSet.get(listPosition).getImage());

    }

    @Override
    public int getItemCount() {
        if (adapterfinder == 0){
            if (dataSet.size() < 3){
                return dataSet.size();
            }else{
                return 3;
            }
        }else
            return dataSet.size();
        }
}
