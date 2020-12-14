package test.test.icheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.FriendsAdapter;
//import test.test.icheck.adapter.productAdapter;
import test.test.icheck.adapter.ProductAdapter;
import test.test.icheck.entity.friends;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.reviews;

interface CompletionHandler {
    public void prodectFetched(List<Product> products);
}

public class HomeFragment extends Fragment {
    private static ProductAdapter adapter;
    IMyService iMyService;
    String json_string;
    TextView seeAllProducts,chatBot,id_textTrendingProducts;
    ImageView id_categoryHome;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Product> productList;
    private static FriendsAdapter adapterf;
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
        productList = new ArrayList<Product>();
        createProductListView(v,productList);
        //createFriendListView(v);
        seeAllProducts = (TextView)v.findViewById(R.id.id_seeAllProducts);
        id_textTrendingProducts=(TextView)v.findViewById(R.id.id_textTrendingProducts);
        id_categoryHome = (ImageView)v.findViewById(R.id.id_categoryHome);
        id_textTrendingProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ArDecorationActivity.class);
                startActivity(intent);
            }
        });
        chatBot = (TextView)v.findViewById(R.id.id_chatBots);
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChatBotActivity.class);
                startActivity(intent);
            }
        });
        seeAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AllProductsActivity.class);
                startActivity(intent);
            }
        });
        id_categoryHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ArCustomFaceFiltreActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
    public void createProductListView(final View v,List<Product> productList){
        getProducts(productList, new CompletionHandler() {
            @Override
            public void prodectFetched(List<Product> products) {
                if (products.size() > 0){
                    adapter = new ProductAdapter(products,getContext());
                    recyclerView = (RecyclerView) v.findViewById(R.id.id_listProcuts);
                    recyclerView.setHasFixedSize(true);
                  //  GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(),2,LinearLayoutManager.VERTICAL,false);
                    layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    seeAllProducts.setText("See All("+productList.size()+")");
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

        adapterf = new FriendsAdapter(friendsList);
        recyclerView = (RecyclerView) v.findViewById(R.id.id_listFriends);
        layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterf);
    }


    public void getProducts(final List<Product> productList, final CompletionHandler handler ){
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        Call <List<Product>> call = iMyService.getProductsTrend();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> products = response.body();
                List<reviews> reviews ;
                for (int i=0;i<products.size();i++){
                    productList.add(products.get(i));
                }
                handler.prodectFetched(productList);
                System.out.println("succes "+productList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("succes");
            }
        });
    }

}