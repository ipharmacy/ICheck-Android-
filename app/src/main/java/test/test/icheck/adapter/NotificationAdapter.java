package test.test.icheck.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.ProductDetailActivity;
import test.test.icheck.R;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.entity.Notification;
import test.test.icheck.entity.Product;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private ArrayList<Notification> dataSet;
    private Context context;
    String connectedUser;
    IMyService iMyService;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_reviewName,textMessageNotification;
        ImageView imageNotification;
        Button acceptNotification,declineNotification;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.id_reviewName = (TextView) itemView.findViewById(R.id.id_notificationName);
            this.textMessageNotification = (TextView) itemView.findViewById(R.id.textMessageNotification);
            this.acceptNotification = (Button) itemView.findViewById(R.id.acceptNotification);
            this.declineNotification = (Button) itemView.findViewById(R.id.declineNotification);
            this.imageNotification = (ImageView) itemView.findViewById(R.id.imageNotification);
        }
    }

    public NotificationAdapter(ArrayList<Notification> data, Context context,String connectedUser) {
        this.dataSet = data;
        this.context = context;
        this.connectedUser = connectedUser;
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_notification_cv, parent, false);
        NotificationAdapter.MyViewHolder myViewHolder = new NotificationAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.MyViewHolder holder, final int listPosition) {

        TextView id_reviewName = holder.id_reviewName;
        TextView textMessageNotification = holder.textMessageNotification;
        Button acceptNotification = holder.acceptNotification;
        Button declineNotification = holder.declineNotification;
        ImageView imageNotification = holder.imageNotification;
        String pathImage="https://polar-peak-71928.herokuapp.com/uploads/users/";
        String fullPath = pathImage+dataSet.get(listPosition).getImage();
     //   String name=dataSet.get(listPosition).getReceiver().getFirstName()+" "+dataSet.get(listPosition).getReceiver().getLastName();
        id_reviewName.setText(dataSet.get(listPosition).getTitle());
        textMessageNotification.setText(dataSet.get(listPosition).getDescription());
        Glide.with(context).load(fullPath).into(imageNotification);
        if (dataSet.get(listPosition).getTitle().equals("Request accepted")){
            acceptNotification.setVisibility(View.GONE);
            declineNotification.setVisibility(View.GONE);
            System.out.println("ldakhel + "+dataSet.get(listPosition).getTitle());
        }

        // productImage.setImageResource(dataSet.get(listPosition).getProductImage());
        //productBrand.setImageResource(dataSet.get(listPosition).getBrandImage());
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId",dataSet.get(listPosition).getId());
                // intent.putExtra("product", (Serializable) dataSet.get(listPosition));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(),productImage, ViewCompat.getTransitionName(productImage));
                v.getContext().startActivity(intent,options.toBundle());
            }
        });*/
        holder.acceptNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofitClient = RetrofitClient.getInstance();
                iMyService = retrofitClient.create(IMyService.class);
                HashMap<String,String> map = new HashMap<>();
                map.put("userId",connectedUser);
                map.put("friendId",dataSet.get(listPosition).getLink());
                map.put("notifId",dataSet.get(listPosition).getId());
                System.out.println("connected User : "+connectedUser);
                System.out.println("friend id : "+dataSet.get(listPosition).getLink());
                System.out.println("notif id : "+dataSet.get(listPosition).getId());
              Call<HashMap<String,String>> call = iMyService.acceptRequest(map);
                call.enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        System.out.println("body response"+response.body());
                        Toast.makeText(context,"Request accepted succesfully",Toast.LENGTH_SHORT).show();
                        acceptNotification.setVisibility(View.INVISIBLE);
                        declineNotification.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.declineNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofitClient = RetrofitClient.getInstance();
                iMyService = retrofitClient.create(IMyService.class);
                HashMap<String,String> map = new HashMap<>();
                map.put("userId",connectedUser);
                map.put("friendId",dataSet.get(listPosition).getLink());
                map.put("notifId",dataSet.get(listPosition).getId());
                Call<HashMap<String,String>> call = iMyService.declineRequest(map);
                call.enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        Toast.makeText(context,"Request deleted succesfully",Toast.LENGTH_SHORT).show();
                        declineNotification.setVisibility(View.INVISIBLE);
                        acceptNotification.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
