package test.test.icheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.friendsAdapter;
//import test.test.icheck.adapter.productAdapter;
import test.test.icheck.adapter.productAdapter;
import test.test.icheck.entity.friends;
import test.test.icheck.entity.product;
import test.test.icheck.entity.reviews;

interface CompletionHandler {
    public void prodectFetched(ArrayList<product> products);
}

public class HomeFragment extends Fragment {
    private static productAdapter adapter;
    IMyService iMyService;
    String json_string;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<product> productList;
    private static friendsAdapter adapterf;
    private static ArrayList<friends> friendsList;
    private SharedPreferences sp ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        productList = new ArrayList<product>();
        createProductListView(v,productList);
        //createFriendListView(v);

        return v;
    }



    public void createProductListView(final View v,ArrayList<product> productList){
        getProducts(productList, new CompletionHandler() {
            @Override
            public void prodectFetched(ArrayList<product> products) {
                if (products.size() > 0){
                    adapter = new productAdapter(products,getContext());
                    recyclerView = (RecyclerView) v.findViewById(R.id.id_listProcuts);
                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                }else{
                    System.out.println("Erreur liste ");
                    Log.e("Home Fragement", "Erreur liste ");
                }
            }
        });
    }
    public void createFriendListView(View v){
        friends f1 = new friends(R.drawable.dhialogo,"nike");
        friends f2 = new friends(R.drawable.youssef,"nike");
        friends f3 = new friends(R.drawable.eya,"nike");
        friends f4 = new friends(R.drawable.hamza,"nike");
        friends f5 = new friends(R.drawable.chekib,"nike");
        friends f6 = new friends(R.drawable.mbarki,"nike");

        friends f7 = new friends(R.drawable.salsabil,"nike");
        friends f8 = new friends(R.drawable.mehdi,"nike");

        friendsList = new ArrayList<friends>();
        friendsList.add(f1);
        friendsList.add(f2);
        friendsList.add(f3);
        friendsList.add(f4);
        friendsList.add(f5);
        friendsList.add(f6);

        friendsList.add(f7);
        friendsList.add(f8);

        adapterf = new friendsAdapter(friendsList);
        recyclerView = (RecyclerView) v.findViewById(R.id.id_listFriends);
        layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterf);
    }


    public void getProducts(final ArrayList<product> productList, final CompletionHandler handler ){
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        Call <List<product>> call = iMyService.getProducts();
        call.enqueue(new Callback<List<product>>() {
            @Override
            public void onResponse(Call<List<product>> call, Response<List<product>> response) {
                //Gson gson = new Gson();
                // List<product> listProduct = (List<product>) gson.fromJson(response.body(),product.class);

                List<product> products = response.body();
                List<reviews> reviews ;
                for (int i=0;i<products.size();i++){
                    productList.add(products.get(i));
                }
                handler.prodectFetched(productList);
                System.out.println("succes "+productList);
            }

            @Override
            public void onFailure(Call<List<product>> call, Throwable t) {
                System.out.println("succes");
            }
        });
    }

}