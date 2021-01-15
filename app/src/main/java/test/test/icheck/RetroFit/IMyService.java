package test.test.icheck.RetroFit;

import java.util.ArrayList;
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
import test.test.icheck.entity.Favorite;
import test.test.icheck.entity.Friendship;
import test.test.icheck.entity.MessageChat;
import test.test.icheck.entity.Notification;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.ProductDetails;

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

    @GET("products/trending")
    Call<List<Product>> getProductsTrend();

    @POST("user/addFavorite")
    Call<HashMap<String,String>> addFavorite(@Body HashMap<String,String> map);
    @POST("user/removeFavorite")
    Call<HashMap<String,String>> removeFavorite(@Body HashMap<String,String> map);
    @POST("products/detail")
    Call<ProductDetails> getProductDetails(@Body HashMap<String,String> map);
    @POST("user/getFavorite")
    Call<Customer> getUserFavorite(@Body HashMap<String,String> map);
    @POST("user/sendVerificationCode")
    Call<HashMap<String,String>> sendVerificationCode(@Body HashMap<String,String> map);

    @POST("user/verifyAccount")
    Call<Customer> verifyAccount(@Body HashMap<String,String> map);
    @POST("products/removeReview")
    Call<HashMap<String,String>> removeReview(@Body HashMap<String,String> map);
    @POST("chat/addMessage")
    Call<HashMap<String,String>> addMessage(@Body HashMap<String,String> map);
    @POST("chat/getMessages")
    Call<ArrayList<MessageChat>> getMessages(@Body HashMap<String,String> map);
    @GET("user/friends")
    Call<ArrayList<Customer>> getAllCustomers();
    @POST("user/addFriendship")
    Call<HashMap<String,String>> sendRequest(@Body HashMap<String,String> map);
    @POST("user/acceptFriendship")
    Call<HashMap<String,String>> acceptRequest(@Body HashMap<String,String> map);
    @POST("user/declineFriendship")
    Call<HashMap<String,String>> declineRequest(@Body HashMap<String,String> map);
    @POST("user/getNotificationsForDhia")
    Call<ArrayList<Notification>> getNotifications(@Body HashMap<String,String> map);
    @POST("user/getFriendship")
    Call<ArrayList<Friendship>> getFriendship(@Body HashMap<String,String> map);

}
