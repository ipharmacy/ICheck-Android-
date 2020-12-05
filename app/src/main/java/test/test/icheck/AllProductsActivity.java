package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.AllProductsAdapter;
import test.test.icheck.adapter.CategoryAdapter;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.reviews;

interface CompletionHandlerAllProducts {
    public void prodectFetched(ArrayList<Product> products);
}
public class AllProductsActivity extends AppCompatActivity {
    private static ArrayList<Product> productList;
    private static ArrayList<String> categoryList;
    private static CategoryAdapter adapter;
    private static AllProductsAdapter adapter2;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager gridLayoutManager;
    private static RecyclerView recyclerView;
    IMyService iMyService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        productList = new ArrayList<Product>();
       loadAllProducts(productList);

    }
    public void getProducts(final ArrayList<Product> productList, final CompletionHandler handler ){
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        Call<List<Product>> call = iMyService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                //Gson gson = new Gson();
                // List<product> listProduct = (List<product>) gson.fromJson(response.body(),product.class);

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
    private void loadAllProducts(final ArrayList<Product> productList) {
        getProducts(productList, new CompletionHandler() {
            @Override
            public void prodectFetched(ArrayList<Product> products) {
                if (products.size() > 0){
                    createCategoryListView(products);
                    createProductListView( products);

                }else{
                    System.out.println("Erreur liste ");
                    Log.e("Home Fragement", "Erreur liste ");
                }
            }
        });
    }

    private void createCategoryListView(ArrayList<Product> products) {
        categoryList = new ArrayList<String>();
        filtreCategory(categoryList,products);
       if(categoryList != null ){
           System.out.println("Category list : "+categoryList);
           adapter = new CategoryAdapter(categoryList);
           recyclerView = (RecyclerView)findViewById(R.id.id_categories_rv);
           recyclerView.setHasFixedSize(true);
           //  GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(),2,LinearLayoutManager.VERTICAL,false);
           layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
           recyclerView.setLayoutManager(layoutManager);
           recyclerView.setItemAnimator(new DefaultItemAnimator());
           recyclerView.setAdapter(adapter);

           adapter.onClickSubject
                   .subscribe(it ->
                                   createProductListView(productList)
                           ,
                   (Throwable onError) -> { },
                   () -> {},
                   on1 -> System.out.println("Observer 1 onSubscribe"
                   ));

       }



    }
    public void createProductListView(ArrayList<Product> products){
        if (products.size() > 0){
            adapter2 = new AllProductsAdapter(products,this);
            recyclerView = (RecyclerView)findViewById(R.id.id_rvallproducts);
            recyclerView.setHasFixedSize(true);
            gridLayoutManager = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
           // layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter2);
        }else{
            System.out.println("Erreur liste ");
            Log.e("Home Fragement", "Erreur liste ");
        }

    }

    private void filtreCategory(ArrayList<String> categoryList,ArrayList<Product> products) {
        for(int i=0;i<products.size();i++){

            if (categoryList.isEmpty()){

                categoryList.add(products.get(i).getCategory());

            }else{

                for (int j=0 ; j<categoryList.size();j++) {

                    if (!products.get(i).getCategory().equals(categoryList.get(j))) {

                        categoryList.add(products.get(i).getCategory());

                    }

                }

            }
        }
    }
}