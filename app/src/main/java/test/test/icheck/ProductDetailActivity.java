package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import test.test.icheck.entity.ProductDetails;
import test.test.icheck.entity.photoProduct;
//import test.test.icheck.adapter.reviewAdapter;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.reviews;

interface CompletionHandlerDetails {
    public void productFetched(Product Product,String isLikedd);
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
    String liked="deleted";
   private static ReviewAdapter adapter2;
    ImageView imageProduct,imageBrand,favorite;
    TextView nameProduct,descriptionProduct,available,rate,seeAllRate;
    Button btnRate;
    String isLiked = "" ;
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
        favorite = (ImageView)findViewById(R.id.id_favorite);
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
                seeAllRate= (TextView)findViewById(R.id.id_seeAllRate);
                btnRate  =(Button)findViewById(R.id.id_rateReviewBtn);


                recyclerView = (RecyclerView)findViewById(R.id.id_listReviews);
                createProduct(Product,isLiked);

            }
    }
    private void createProduct(test.test.icheck.entity.Product Product,String isLiked) {
        getProduct(Product,isLiked,new CompletionHandlerDetails(){
            @Override
            public void productFetched(final test.test.icheck.entity.Product Product,String isLiked) {
                if (Product != null){
                    Glide.with(getApplicationContext()).load(pathImage+Product.getImages().get(0)).into(imageProduct);
                    Glide.with(getApplicationContext()).load("https://polar-peak-71928.herokuapp.com/uploads/brands/"+Product.getBrand()+".jpg").into(imageBrand);
                    nameProduct.setText(Product.getName());
                    descriptionProduct.setText(Product.getAddress());
                    available.setText(Product.getAvailable());
                    rate.setText(Double.toString(Product.getRate()));
                    seeAllRate.setText("See All ("+Product.getReviews().size()+")");
                    ArrayList<String> listPhotos = new ArrayList<>();
                    System.out.println("isLiked : +0"+isLiked);


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
                    seeAllRate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ProductDetailActivity.this,ProductDetailsReviews.class);
                            intent.putExtra("productId",productId);
                            startActivity(intent);
                        }
                    });
                    System.out.println("is liked louta : "+isLiked);
                    if(isLiked.equals("1")){
                        favorite.setImageResource(R.drawable.ic_favoriteliked_24);
                        System.out.println("isLiked Here : "+isLiked);
                    }else if (isLiked.equals("0")){
                        favorite.setImageResource(R.drawable.ic_favorite24);
                    }
                    favorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isLiked.equals("0")){
                                addFavorite();
                            }else{
                                removeFavorite();
                            }


                        }
                    });
                }
            }
        });

    }

    private void createReviewsListView(ArrayList<reviews> listReviews) {
        adapter2 = new ReviewAdapter(listReviews,getApplicationContext(),0);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    public void  getProduct(final test.test.icheck.entity.Product Product, String isLiked, final CompletionHandlerDetails handler){
            Retrofit retrofitClient = RetrofitClient.getInstance();
            iMyService = retrofitClient.create(IMyService.class);
       HashMap<String,String> map = new HashMap<>();
       map.put("prodId",productId);
       map.put("userId",sp.getString("userId",""));
            Call<ProductDetails> call = iMyService.getProductDetails(map);
            call.enqueue(new Callback<ProductDetails>() {
                @Override
                public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                    ProductDetails productDetailsFromServer = response.body();
                    handler.productFetched(productDetailsFromServer.getProduct(),productDetailsFromServer.getIsLiked());
                 //   ProductDetailActivity.this.isLiked = productDetailsFromServer.getIsLiked();
                    System.out.println("Response From server : "+productDetailsFromServer.getProduct());
                    System.out.println("is liked : "+ ProductDetailActivity.this.isLiked);
                }

                @Override
                public void onFailure(Call<ProductDetails> call, Throwable t) {
                    System.out.println("SERVER FAILED");
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
  public void addFavorite(){
      Retrofit retrofitClient = RetrofitClient.getInstance();
      iMyService = retrofitClient.create(IMyService.class);
      HashMap<String,String> map = new HashMap<>();
      map.put("prodId",productId);
      map.put("userId",sp.getString("userId",""));
      Call <HashMap<String,String>> call = iMyService.addFavorite(map);
      call.enqueue(new Callback<HashMap<String, String>>() {
          @Override
          public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
              System.out.println("Reponse favoris : "+response.body());
              Toast.makeText(getApplicationContext(),"Added succesfuly",Toast.LENGTH_SHORT).show();
              favorite.setImageResource(R.drawable.ic_favoriteliked_24);
              isLiked = "1";

          }

          @Override
          public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
              System.out.println("Failed favoris ");
          }
      });

  }
  private void removeFavorite(){
      Retrofit retrofitClient = RetrofitClient.getInstance();
      iMyService = retrofitClient.create(IMyService.class);
      HashMap<String,String> map = new HashMap<>();
      map.put("prodId",productId);
      map.put("userId",sp.getString("userId",""));
      Call <HashMap<String,String>> call = iMyService.removeFavorite(map);
      call.enqueue(new Callback<HashMap<String, String>>() {
          @Override
          public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
              favorite.setImageResource(R.drawable.ic_favorite24);
              isLiked="0";
          }

          @Override
          public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

          }
      });
  }

}