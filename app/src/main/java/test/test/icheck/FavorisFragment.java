package test.test.icheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.FavorisAdapter;
import test.test.icheck.adapter.ProductAdapter;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.Product;

import static android.content.Context.MODE_PRIVATE;


public class FavorisFragment extends Fragment {

    private static FavorisAdapter adapter;
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sp ;
    ImageView favorites;
    IMyService iMyService;
    TextView messageNoProduct;
    public FavorisFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favoris, container, false);
        favorites = (ImageView)v.findViewById(R.id.id_cv_favoris);
        messageNoProduct = (TextView)v.findViewById(R.id.id_noproducts);
        messageNoProduct.setVisibility(View.GONE);
        sp = v.getContext().getSharedPreferences(MainActivity.FILE_NAME,MODE_PRIVATE);
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("userId",sp.getString("userId",""));
        Call<Customer> call = iMyService.getUserFavorite(map);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customer= response.body();
                if (customer.getFavorites().size()==0){
                    messageNoProduct.setVisibility(View.VISIBLE);
                }else{
                    ArrayList<Product> products = new ArrayList<>();
                    //products.addAll()
                    for(int i=0;i<customer.getFavorites().size();i++){
                        products.add(customer.getFavorites().get(i).getProduct());
                    }
                    createProductListView(products,v);
                }

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
        return v;
    }

    private void createProductListView(ArrayList<Product> products,View v) {
        adapter = new FavorisAdapter(products,getContext());
        recyclerView = (RecyclerView)v.findViewById(R.id.id_rvfavoris);
        recyclerView.setHasFixedSize(true);
        //  GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(),2,LinearLayoutManager.VERTICAL,false);
        layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}