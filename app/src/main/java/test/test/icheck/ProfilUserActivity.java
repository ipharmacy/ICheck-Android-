package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import test.test.icheck.entity.Friendship;

public class ProfilUserActivity extends AppCompatActivity {
    TextView firstName,lastName,email,phone;
    Button btnSendRequest,btnDeleteRequest;
    String id;
    ImageView avatar;
    IMyService iMyService;
    public static final String FILE_NAME = "test.test.icheck.shared";
    private SharedPreferences Preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        Bundle extras = getIntent().getExtras();
        Preferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        firstName = (TextView)findViewById(R.id.textView2f);
        email = (TextView)findViewById(R.id.textView3f);
        avatar = (ImageView)findViewById(R.id.roundedImageViewf);
        btnSendRequest = (Button)findViewById(R.id.buttonf);
        btnDeleteRequest= (Button)findViewById(R.id.buttonf2);
        id= extras.getString("senderId");
        firstName.setText(extras.getString("firstName")+extras.getString("lastName"));
        email.setText(extras.getString("email"));
        Glide.with(this).load("https://polar-peak-71928.herokuapp.com/uploads/users/"+extras.getString("avatar")).into(avatar);
        searchForCustomers();
        btnDeleteRequest.setVisibility(View.INVISIBLE);
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofitClient = RetrofitClient.getInstance();
                iMyService = retrofitClient.create(IMyService.class);
                HashMap<String,String> map = new HashMap<>();
                map.put("userId",Preferences.getString("userId",""));
                map.put("friendId",extras.getString("id"));
                Call<HashMap<String,String>> call = iMyService.sendRequest(map);
                call.enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                        Toast.makeText(getApplicationContext(),"Request send succesfully",Toast.LENGTH_SHORT).show();
                        btnDeleteRequest.setVisibility(View.VISIBLE);
                        btnSendRequest.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void searchForCustomers() {

    }

}