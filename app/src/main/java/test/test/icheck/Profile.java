package test.test.icheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.ProductDetails;

import static android.content.Context.MODE_PRIVATE;
public class Profile extends Fragment {

    Button btn1 ;
    TextView firstName,email,productName,productDescription,seeAllProduct;
    ImageView avatar,producImage,imageBrand;
    String pathImage="https://polar-peak-71928.herokuapp.com/uploads/users/";
    String pathImageProduct="https://polar-peak-71928.herokuapp.com/uploads/products/";
    String pathImageProductBrand="https://polar-peak-71928.herokuapp.com/uploads/brands/";
    String avatarName;
     IMyService iMyService;
    private SharedPreferences sp,sharedp ;
    public static final String FILE_NAME = "test.test.icheck.shared";

    public Profile() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        btn1 = (Button)v.findViewById(R.id.button2);
        firstName = (TextView)v.findViewById(R.id.textView2);
        email = (TextView)v.findViewById(R.id.textView3);
        avatar = (ImageView)v.findViewById(R.id.roundedImageView);
        producImage = (ImageView)v.findViewById(R.id.imageView3);
        imageBrand = (ImageView)v.findViewById(R.id.id_productBrand2);
        productName = (TextView)v.findViewById(R.id.id_productName2);
        productDescription = (TextView)v.findViewById(R.id.id_productAddress2);
        seeAllProduct = (TextView)v.findViewById(R.id.id_seelAllProfilImage);
        createFavoriteProduct();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionDestroy(v);
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
             }
        });
      sp = v.getContext().getSharedPreferences(MainActivity.FILE_NAME,MODE_PRIVATE);
        System.out.println( "email shared : "+sp.getString("email",""));
        email.setText(sp.getString("email",""));
      firstName.setText(sp.getString("firstName","")+" "+sp.getString("lastName",""));
        avatarName = sp.getString("avatar","");
        Glide.with(this.getContext()).load(pathImage+avatarName).into(avatar);

        return v;
    }

    private void createFavoriteProduct() {
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        sharedp = getContext().getSharedPreferences(MainActivity.FILE_NAME,MODE_PRIVATE);
        HashMap<String,String> map = new HashMap<>();
        map.put("userId",sharedp.getString("userId",""));
        Call<Customer> call = iMyService.getUserFavorite(map);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer customer= response.body();
                System.out.println("Favorite : "+customer.getFavorites().size());
                if (customer.getFavorites().size() == 0 ){
                    productDescription.setVisibility(View.GONE);
                    imageBrand.setVisibility(View.GONE);
                    productName.setText("You have 0 favorite products");
                    seeAllProduct.setVisibility(View.GONE);
                }else{
                    Glide.with(getContext()).load(pathImageProduct+customer.getFavorites().get(0).getProduct().getImages().get(0)).into(producImage);
                    Glide.with(getContext()).load(pathImageProductBrand+customer.getFavorites().get(0).getProduct().getBrand()+".jpg").into(imageBrand);
                    productName.setText(customer.getFavorites().get(0).getProduct().getName());
                    productDescription.setText(customer.getFavorites().get(0).getProduct().getDescription());
                    seeAllProduct.setText("See All("+customer.getFavorites().size()+")");
                    seeAllProduct.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment fragment = new FavorisFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.Fcontainer, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    producImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                            intent.putExtra("productId",customer.getFavorites().get(0).getProduct().getId());
                            // intent.putExtra("product", (Serializable) dataSet.get(listPosition));
                            v.getContext().startActivity(intent);
                        }
                    });
                }
                    System.out.println("customer : "+response.body());
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                System.out.println("kkkk");
            }
        });
    }
    public void sessionDestroy(View v){
        sp=v.getContext().getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("email");
        editor.remove("firstName");
        editor.remove("lastName");
        editor.clear();
        editor.apply();
    }
}