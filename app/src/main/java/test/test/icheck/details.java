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
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.photoAdapter;
import test.test.icheck.adapter.reviewAdapter;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.photoProduct;
//import test.test.icheck.adapter.reviewAdapter;
import test.test.icheck.entity.product;
import test.test.icheck.entity.reviews;

interface CompletionHandlerDetails {
    public void productFetched(product Product);
}
public class details extends AppCompatActivity {
    ArrayList<photoProduct> photoList;
    ArrayList<reviews> reviewList;
    IMyService iMyService;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static RecyclerView recyclerView3;
    private static photoAdapter adapter;
    private static product Product;
    String productId;
   private static reviewAdapter adapter2;
    ImageView imageProduct,imageBrand;
    TextView nameProduct,descriptionProduct,available,rate,seeAll;
    EditText ReviewInput;
    RatingBar rateBar;
    Button submitRate;
    String pathImage="https://polar-peak-71928.herokuapp.com/uploads/products/";
    private SharedPreferences sp ;
    public static final String FILE_NAME = "test.test.icheck.shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
     //   createFriendListView();
       // creatReviewListView();

            sp = getApplicationContext().getSharedPreferences(MainActivity.FILE_NAME,MODE_PRIVATE);
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                productId= null;
                System.out.println("productId : Null ");

            } else {
                productId= extras.getString("productId");
                //Product2 = (product) getIntent().getSerializableExtra("product");
                System.out.println( "productId : "+productId);
               // System.out.println( "product2 : "+Product2);
                Product = new product();
                imageProduct = (ImageView)findViewById(R.id.id_imageProduit);
                imageBrand = (ImageView)findViewById(R.id.id_productBrand);
                nameProduct = (TextView) findViewById(R.id.id_productName);
                descriptionProduct = (TextView) findViewById(R.id.id_productAddress);
                nameProduct = (TextView) findViewById(R.id.id_productName);
                available = (TextView) findViewById(R.id.id_produitAvailable);
                rate = (TextView) findViewById(R.id.id_productRate);
                seeAll= (TextView)findViewById(R.id.id_seeAllPhotos);
                ReviewInput = (EditText)findViewById(R.id.editTextTextPersonName) ;
                rateBar = (RatingBar) findViewById(R.id.ratingBar);
                submitRate = (Button)findViewById(R.id.button4);
                recyclerView = (RecyclerView)findViewById(R.id.id_listReviews);

                createProduct(Product);


            }
    }

    private void createProduct(product Product) {
        getProduct(Product,new CompletionHandlerDetails(){

            @Override
            public void productFetched(final product Product) {
                if (Product != null){
                    Glide.with(getApplicationContext()).load(pathImage+Product.getImages().get(0)).into(imageProduct);
                    Glide.with(getApplicationContext()).load("https://polar-peak-71928.herokuapp.com/uploads/brands/"+Product.getBrand()+".jpg").into(imageBrand);
                    nameProduct.setText(Product.getName());
                    descriptionProduct.setText(Product.getAddress());
                    available.setText(Product.getAvailable());
                    rate.setText(Double.toString(Product.getRate()));
                    seeAll.setText("See all ("+Product.getImages().size()+")");
                    ArrayList<String> listPhotos = new ArrayList<>();
                    for(int i=0;i<Product.getImages().size();i++){
                        listPhotos.add(Product.getImages().get(i));
                    }
                    if (listPhotos !=null){
                        createPhotosListView(listPhotos);
                    }else{
                        System.out.println("Failed load image");
                    }
                    submitRate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String rate = Float.toString(rateBar.getRating());
                            final String review = ReviewInput.getText().toString();
                            Retrofit retrofitClient = RetrofitClient.getInstance();
                            iMyService = retrofitClient.create(IMyService.class);
                            HashMap<String,String> map = new HashMap<>();
                            map.put("prodId",Product.getId());
                            map.put("review",review);
                            map.put("userId",sp.getString("userId",""));
                            map.put("rate",rate);
                            Call<HashMap<String,String>> call = iMyService.addReview(map);
                            call.enqueue(new Callback<HashMap<String, String>>() {
                                @Override
                                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                                   /* Customer customer = new Customer();
                                    customer.setFirstName(sp.getString("firstName",""));
                                    customer.setLastName(sp.getString("lastName",""));
                                    customer.setAvatar(sp.getString("avatar",""));
                                    reviews Rev = new reviews();
                                    Rev.setId(response.body().get("message"));
                                    Rev.setRate(Double.valueOf(rate));
                                    Rev.setReview(review);
                                    Rev.setUser(customer);
                                    reviewList.add(Rev);

                                   */
                                   // adapter2.updateData(reviewList);
                                  //  recyclerView.smoothScrollToPosition(adapter2.getItemCount()-1);
                                    Toast.makeText(details.this, "Sucess  "+response.body().get("message"), Toast.LENGTH_SHORT).show();
                                    System.out.println("reviewList : "+reviewList);
                                }

                                @Override
                                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                                    Toast.makeText(details.this, "FAIL ", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                    reviewList = Product.getReviews();
                    createReviewsListView(Product.getReviews());
                }
            }
        });

    }

    private void createReviewsListView(ArrayList<reviews> listReviews) {
        adapter2 = new reviewAdapter(listReviews,getApplicationContext());

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    public void  getProduct(final product Product,final CompletionHandlerDetails handler){
            Retrofit retrofitClient = RetrofitClient.getInstance();
            iMyService = retrofitClient.create(IMyService.class);
       HashMap<String,String> map = new HashMap<>();
       map.put("prodId",productId);
            Call<product> call = iMyService.findById(map);
            call.enqueue(new Callback<product>() {
                @Override
                public void onResponse(Call<product> call, Response<product> response) {
                    product ProductFromServer = response.body();
                    handler.productFetched(ProductFromServer);
                    System.out.println("Product FROM SERVER : "+ProductFromServer+"Response : "+response);
                }

                @Override
                public void onFailure(Call<product> call, Throwable t) {
                    System.out.println("Request failed");
                }

            });

   }

  public void createPhotosListView(ArrayList<String > listPhotos){
     adapter = new photoAdapter(listPhotos,getApplicationContext());
      recyclerView3 = (RecyclerView)findViewById(R.id.id_listPhotos);
      layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
      recyclerView3.setHasFixedSize(true);
      recyclerView3.setLayoutManager(layoutManager);
      recyclerView3.setItemAnimator(new DefaultItemAnimator());
      recyclerView3.setAdapter(adapter);
  }

}