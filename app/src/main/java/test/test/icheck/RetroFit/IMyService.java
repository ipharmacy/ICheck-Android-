package test.test.icheck.RetroFit;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.Product;

public interface IMyService {
    @POST("user/register")
    Call<Customer> register(@Body HashMap<String,String> map);

    @POST("user/login")
    Call<Customer> executeLogin(@Body HashMap<String,String> map);
    @Multipart
    @POST("user/upload")
    Call<ResponseBody> postImage(@Part MultipartBody.Part File, @Part("avatar") RequestBody name);
    @POST("user/updateAvatar")
    Call<ResponseBody> updateAvatar(@Body HashMap<String,String> map);

    @GET("products/")
    Call<List<Product>> getProducts();

    @POST("products/id")
    Call<Product> findById(@Body HashMap<String,String> map);

    @POST("products/addReview")
    Call<HashMap<String,String>> addReview(@Body HashMap<String,String> map);

}
