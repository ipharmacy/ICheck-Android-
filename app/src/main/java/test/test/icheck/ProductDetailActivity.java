package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.PhotoAdapter;
import test.test.icheck.adapter.ReviewAdapter;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.photoProduct;
//import test.test.icheck.adapter.reviewAdapter;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.reviews;

interface CompletionHandlerDetails {
    public void productFetched(Product Product);
}
public class ProductDetailActivity extends AppCompatActivity {
    ArrayList<photoProduct> photoList;
    ArrayList<reviews> reviewList;
    IMyService iMyService;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static RecyclerView recyclerView3;
    private static PhotoAdapter adapter;
    private static test.test.icheck.entity.Product Product;
    String productId;
    String review = null;
    String rateText = null;
   private static ReviewAdapter adapter2;
    ImageView imageProduct,imageBrand;
    TextView nameProduct,descriptionProduct,available,rate,seeAll,seeAllRate;
    Button btnRate ;
    String pathImage="https://polar-peak-71928.herokuapp.com/uploads/products/";
    private SharedPreferences sp ;
    public static final String FILE_NAME = "test.test.icheck.shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
     //   createFriendListView();
       // creatReviewListView();
        //userConnected = new Customer();
            sp = getApplicationContext().getSharedPreferences(MainActivity.FILE_NAME,MODE_PRIVATE);
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                productId= null;
                System.out.println("productId : Null ");

            } else {
                productId= extras.getString("productId");
                review = extras.getString("review");
                rateText = extras.getString("rate");
                //Product2 = (product) getIntent().getSerializableExtra("product");
                System.out.println( "productId : "+productId+" review : "+review);
               // System.out.println( "product2 : "+Product2);
                Product = new Product();
                imageProduct = (ImageView)findViewById(R.id.id_imageProduit);
                imageBrand = (ImageView)findViewById(R.id.id_productBrand);
                nameProduct = (TextView) findViewById(R.id.id_productName);
                descriptionProduct = (TextView) findViewById(R.id.id_productAddress);
                nameProduct = (TextView) findViewById(R.id.id_productName);
                available = (TextView) findViewById(R.id.id_produitAvailable);
                rate = (TextView) findViewById(R.id.id_productRate);
                seeAll= (TextView)findViewById(R.id.id_seeAllPhotos);
                seeAllRate= (TextView)findViewById(R.id.id_seeAllRate);
                btnRate  =(Button)findViewById(R.id.id_rateReviewBtn);
                recyclerView = (RecyclerView)findViewById(R.id.id_listReviews);
                createProduct(Product);
            }
    }
    private void createProduct(test.test.icheck.entity.Product Product) {
        getProduct(Product,new CompletionHandlerDetails(){
            @Override
            public void productFetched(final test.test.icheck.entity.Product Product) {
                if (Product != null){
                    Glide.with(getApplicationContext()).load(pathImage+Product.getImages().get(0)).into(imageProduct);
                    Glide.with(getApplicationContext()).load("https://polar-peak-71928.herokuapp.com/uploads/brands/"+Product.getBrand()+".jpg").into(imageBrand);
                    nameProduct.setText(Product.getName());
                    descriptionProduct.setText(Product.getAddress());
                    available.setText(Product.getAvailable());
                    rate.setText(Double.toString(Product.getRate()));
                    seeAll.setText("See all ("+Product.getImages().size()+")");
                    seeAllRate.setText("See All ("+Product.getReviews().size()+")");
                    ArrayList<String> listPhotos = new ArrayList<>();
                    for(int i=0;i<Product.getImages().size();i++){
                        listPhotos.add(Product.getImages().get(i));
                    }
                    if (listPhotos !=null){
                        createPhotosListView(listPhotos);
                    }else{
                        System.out.println("Failed load image");
                    }
                    btnRate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BottomSheetReview   BottomSheet = new BottomSheetReview();
                            Bundle args = new Bundle();
                            args.putString("prodId",Product.getId());
                            args.putString("userId",sp.getString("userId",""));
                            BottomSheet.setArguments(args);
                            BottomSheet.show(getSupportFragmentManager(),"TAG");
                        }
                    });
                    reviewList = Product.getReviews();
                    createReviewsListView(Product.getReviews());
                }
            }
        });

    }

    private void createReviewsListView(ArrayList<reviews> listReviews) {
        adapter2 = new ReviewAdapter(listReviews,getApplicationContext());

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    public void  getProduct(final test.test.icheck.entity.Product Product, final CompletionHandlerDetails handler){
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

  public void createPhotosListView(ArrayList<String > listPhotos){
     adapter = new PhotoAdapter(listPhotos,getApplicationContext());
      recyclerView3 = (RecyclerView)findViewById(R.id.id_listPhotos);
      layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
      recyclerView3.setHasFixedSize(true);
      recyclerView3.setLayoutManager(layoutManager);
      recyclerView3.setItemAnimator(new DefaultItemAnimator());
      recyclerView3.setAdapter(adapter);
  }

}