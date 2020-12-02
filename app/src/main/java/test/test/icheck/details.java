package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
    private static photoAdapter adapter;
    private static product Product;
    String productId;
   // private static reviewAdapter adapter2;
    ImageView imageProduct,imageBrand;
    TextView nameProduct,descriptionProduct,available,rate,seeAll;
    String pathImage="https://polar-peak-71928.herokuapp.com/uploads/products/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
     //   createFriendListView();
       // creatReviewListView();

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
                createProduct(Product);


            }
    }

    private void createProduct(product Product) {
        getProduct(Product,new CompletionHandlerDetails(){

            @Override
            public void productFetched(product Product) {
                if (Product != null){
                    Glide.with(getApplicationContext()).load(pathImage+Product.getImages().get(0)).into(imageProduct);
                    Glide.with(getApplicationContext()).load(pathImage+Product.getImages().get(0)).into(imageBrand);
                    nameProduct.setText(Product.getName());
                    descriptionProduct.setText(Product.getAddress());
                    available.setText(Product.getAvailable());
                    rate.setText(Product.getRate());
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

                }
            }
        });

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
      recyclerView = (RecyclerView)findViewById(R.id.id_listPhotos);
      layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(layoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setAdapter(adapter);
  }
   /* public void createFriendListView(){
        photoProduct f1 = new photoProduct(R.drawable.nikeair,"nike");
        photoList = new ArrayList<photoProduct>();
        photoList.add(f1);
        photoList.add(f1);
        photoList.add(f1);
        photoList.add(f1);
        photoList.add(f1);
        photoList.add(f1);

        adapter = new photoAdapter(photoList);
        recyclerView = (RecyclerView)findViewById(R.id.id_listPhotos);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    public void creatReviewListView(){
         reviews f1 = new reviews(R.drawable.dhialogo,5,"Dhia Ben Hamouda","I liked that they arrived promptly and they were a perfect fit");
        reviewList = new ArrayList<reviews>();
        reviewList.add(f1);
        reviewList.add(f1);
        reviewList.add(f1);
        adapter2 = new reviewAdapter(reviewList);
        recyclerView = (RecyclerView)findViewById(R.id.id_listReviews);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter2);
    }
*/
}