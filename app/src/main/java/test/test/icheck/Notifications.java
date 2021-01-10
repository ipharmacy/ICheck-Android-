package test.test.icheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.FriendsAdapter;
import test.test.icheck.adapter.NotificationAdapter;
import test.test.icheck.adapter.SearchUserAdapter;
import test.test.icheck.entity.Notification;

import static android.content.Context.MODE_PRIVATE;


public class Notifications extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static NotificationAdapter adapterf;
    public static final String FILE_NAME = "test.test.icheck.shared";
    private SharedPreferences sp;
    IMyService iMyService;
    public Notifications() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        sp = getContext().getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        HashMap<String,String> map = new HashMap<>();
        System.out.println("user id : "+sp.getString("userId",""));
        map.put("userId",sp.getString("userId",""));
        Call <ArrayList<Notification>> call = iMyService.getNotifications(map);
        call.enqueue(new Callback<ArrayList<Notification>>() {
            @Override
            public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                ArrayList<Notification> allNotif = new ArrayList<>();
                ArrayList<Notification> realNotif = new ArrayList<>();
                allNotif = response.body();
                adapterf = new NotificationAdapter(allNotif,getContext(),sp.getString("userId",""));
                recyclerView = (RecyclerView) v.findViewById(R.id.id_notificationrv);
                layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.VERTICAL, false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapterf);
                System.out.println("In on response");
            }

            @Override
            public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                System.out.println("Failed to load");
            }
        });

        
        return v;
    }
}