package test.test.icheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.ReviewAdapter;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.reviews;
interface CompletionHandlerDetailsReview {
    public void productFetched(Product Product);
}
public class ProductDetailsReviews extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ReviewAdapter adapter2;
    private String productId;
    IMyService iMyService;
    ArrayList<reviews> reviewList;
    private static test.test.icheck.entity.Product Product;
    Product getproduct = new Product();
    private SharedPreferences sp ;
    public static final String FILE_NAME = "test.test.icheck.shared";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_reviews);
        recyclerView = (RecyclerView)findViewById(R.id.id_rvAllReviews);
        Bundle extras = getIntent().getExtras();
        sp = getApplicationContext().getSharedPreferences(MainActivity.FILE_NAME,MODE_PRIVATE);
        if(extras == null) {
            productId= null;
            System.out.println("productId : Null ");

        } else {
            Product = new Product();
            productId= extras.getString("productId");
            createProduct(Product);
        }

    }
    private void createReviewsListView(ArrayList<reviews> listReviews) {
        adapter2 = new ReviewAdapter(listReviews,getApplicationContext(),1);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

    }
    ItemTouchHelper.SimpleCallback itemTouchHelper =
            new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            if (getproduct.getReviews().get(viewHolder.getAdapterPosition()).getUser().getId().equals(sp.getString("userId",""))){
                Retrofit retrofitClient = RetrofitClient.getInstance();
                iMyService = retrofitClient.create(IMyService.class);
                String ReviewId=getproduct.getReviews().get(viewHolder.getAdapterPosition()).getId();
                System.out.println("Review Id "+ReviewId);
                System.out.println("Product Id "+productId);
                HashMap<String,String> map = new HashMap<>();
                map.put("prodId",productId);
                map.put("reviewId",ReviewId);
                Call<HashMap<String, String>> call = iMyService.removeReview(map);
                call.enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        System.out.println("Review deleted succus "+response.body());
                        reviewList.remove(viewHolder.getAdapterPosition());
                        adapter2.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

                    }
                });
            }
        }
                @Override
                public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    if (!(getproduct.getReviews().get(viewHolder.getAdapterPosition()).getUser().getId().equals(sp.getString("userId","")))){
                        System.out.println("DONT SWIPE");
                        return 0;}
                    return super.getSwipeDirs(recyclerView, viewHolder);
                }
    };

    private void createProduct(test.test.icheck.entity.Product Product) {
        getProduct(Product,new CompletionHandlerDetailsReview(){
            @Override
            public void productFetched(final test.test.icheck.entity.Product Product) {
                if (Product != null){
                    getproduct = Product;
                    reviewList = Product.getReviews();
                    createReviewsListView(Product.getReviews());
                }
            }
        });

    }
    public void  getProduct(final test.test.icheck.entity.Product Product, final CompletionHandlerDetailsReview handler){
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("prodId",productId);
        Call<test.test.icheck.entity.Product> call = iMyService.findById(map);
        call.enqueue(new Callback<test.test.icheck.entity.Product>() {
            @Override
            public void onResponse(Call<test.test.icheck.entity.Product> call, Response<test.test.icheck.entity.Product> response) {
                test.test.icheck.entity.Product ProductFromServer = response.body();
                handler.productFetched(ProductFromServer);
                System.out.println("Product FROM SERVER : "+ProductFromServer+"Response : "+response);
            }

            @Override
            public void onFailure(Call<test.test.icheck.entity.Product> call, Throwable t) {
                System.out.println("Request failed");
            }

        });

    }
}
