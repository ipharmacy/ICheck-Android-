package test.test.icheck.RetroFit;

import java.util.HashMap;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import test.test.icheck.entity.Customer;

public interface IMyService {
    @POST("register")
   @FormUrlEncoded
    Observable<String> register(@Field("firstName") String firstName,
                                    @Field("lastName") String lastName,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("phone") String phone,
                                    @Field("sexe") String sexe);


    @POST("login")
    Call<Customer> executeLogin(@Body HashMap<String,String> map);
    // @FormUrlEncoded
    //Observable<String> login(@Field("email") String email,@Field("password") String password);*/
    /*@POST("login")
    @FormUrlEncoded
    Call<Customer> login(@Field("email") String email, @Field("password") String password);*/
}
