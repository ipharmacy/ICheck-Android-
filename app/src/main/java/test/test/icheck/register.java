package test.test.icheck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.entity.Customer;

public class register extends AppCompatActivity  {

    private static final int PERMISSION_REQUEST=0;
    private static final int RESULT_LOAD_IMAGE=1;

    TextView login;
    IMyService iMyService;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    private SharedPreferences sp ;
    public static final String FILE_NAME = "test.test.icheck.shared";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        Register();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private void Register() {
        login = (TextView)findViewById(R.id.id_register);
        firstName = (EditText)findViewById(R.id.id_name);
        lastName = (EditText)findViewById(R.id.id_lastName);
        email = (EditText)findViewById(R.id.id_email);
        password = (EditText)findViewById(R.id.id_password);
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(firstName.getText().toString())){
                    Toast.makeText(v.getContext(),"firstName is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(lastName.getText().toString())){
                    Toast.makeText(v.getContext(),"lastName is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(v.getContext(),"email is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(v.getContext(),"password is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString());



            }
        });
    }
    private void registerUser(String firstname, final String lastname, final String email, String password) {

        System.out.println("user "+" "+firstname+ " " +lastname+" "+email+" "+password);

        HashMap<String,String> map = new HashMap<>();
        map.put("firstName",firstname);
        map.put("lastName",lastname);
        map.put("email",email);
        map.put("password",password);
        Call<Customer> called = iMyService.register(map);
        called.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                if (response.code()== 200 ) {
                    Toast.makeText(register.this, "Success ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register.this,registerimage.class);
                    intent.putExtra("email",email);
                    intent.putExtra("firstname",firstname);
                    intent.putExtra("lastname",lastname);
                    startActivity(intent);
                    //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
                else if (response.code()== 201 ) {
                    Toast.makeText(register.this, "User already exists ! ", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(register.this, "FAIL ", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void sessionDestroy(){
        sp= getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("email");
        editor.remove("firstName");
        editor.remove("lastName");
        editor.clear();
        editor.apply();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}