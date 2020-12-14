package test.test.icheck;

import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
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
    EditText searchProduct;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
         searchView = (SearchView) findViewById(R.id.searchView);
        productList = new ArrayList<Product>();
       loadAllProducts(productList);
    }
    public void getProducts(final List<Product> productList, final CompletionHandler handler ){
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
    private void loadAllProducts(final List<Product> productList) {
        getProducts(productList, new CompletionHandler() {
            @Override
            public void prodectFetched(List<Product> products) {
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
    private void createCategoryListView(List<Product> products) {
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

           Disposable disposable = adapter.onClickSubject
           .subscribeWith(new DisposableObserver<String>() {
                       @Override
                       public void onNext(@NonNull String s) {
                           System.out.println("Categoryy : "+s);
                           ArrayList<Product> productArrayList = new ArrayList<Product>();
                           for (int i =0 ; i < productList.size() ; i++  ){
                               if (productList.get(i).getCategory().equals(s)){
                                   productArrayList.add(productList.get(i));
                               }
                           }
                           createProductListView(productArrayList);
                       }

                       @Override
                       public void onError(@NonNull Throwable e) {
                       }

                       @Override
                       public void onComplete() {
                       }
                   });
           //disposable.dispose();
       }
    }
    public void createProductListView(List<Product> products){
        if (products.size() > 0){
            adapter2 = new AllProductsAdapter(products,this);
            adapter2.notifyDataSetChanged();
            recyclerView = (RecyclerView)findViewById(R.id.id_rvallproducts);
            recyclerView.setHasFixedSize(true);
            gridLayoutManager = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
           // layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter2);
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter2.getFilter().filter(newText);
                    return false;
                }
            });
        }else{
            System.out.println("Erreur liste ");
            Log.e("Home Fragement", "Erreur liste ");
        }
    }
    private void filtreCategory(List<String> categoryList,List<Product> products) {
        for(int i=0;i<products.size();i++){
            if (categoryList.isEmpty()){
                categoryList.add(products.get(i).getCategory());
            }else{
                if(!(categoryList.contains(products.get(i).getCategory()))){
                    categoryList.add(products.get(i).getCategory());
                }
            }
        }
    }

}
