package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.entity.Customer;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView newAccount;
    Button signin;
    EditText email;
    EditText password;
    private Gson gson;
    public Customer customer;
    private Retrofit retrofit;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    public static final String FILE_NAME = "test.test.icheck.shared";
    private SharedPreferences Preferences;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Preferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        if (Preferences.contains("email")){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            login();
            signIn();
        }
    }
    private void signIn() {
        signin = (Button) findViewById(R.id.id_login);
        email = (EditText)findViewById(R.id.id_email);
        password = (EditText)findViewById(R.id.id_password);
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(email.getText().toString(),password.getText().toString());
                /*Intent intent = new Intent(MainActivity.this, home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });
    }

    private void loginUser(final String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email is Empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Password is Empty",Toast.LENGTH_SHORT).show();
            return;
        }

       /* compositeDisposable.add( iMyService.login(email, password)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String response) throws Exception {


                       // customer = onResponse(response);
                        Toast.makeText(MainActivity.this,""+response,Toast.LENGTH_SHORT).show();
                        //System.out.println("GSON !!!!!! : "+customer);


                    }
                })
        );*/
        HashMap<String,String> map = new HashMap<>();
        map.put("email",email);
        map.put("password",password);
        Call<Customer> call = iMyService.executeLogin(map);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                Customer result = response.body();
                if (response.code()== 200 ) {
                    SharedPreferences.Editor editor = Preferences.edit();
                    if (result != null) {
                        editor.putString("email",result.getEmail());
                        editor.putString("firstName",result.getFirstName());
                        editor.putString("lastName",result.getLastName());
                        editor.putString("phone",result.getPhone());
                        editor.putString("sexe",result.getSexe());
                        editor.putString("avatar",result.getAvatar());
                        editor.putString("userId",result.getId());
                        editor.apply();
                        System.out.println( "Email back"+result.getEmail()+result.getFirstName()+" IDD MAWJOUD : "+result.getId());
                        Toast.makeText(MainActivity.this, "Welcome "+result.getFirstName()+" "+result.getLastName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                    //      editor.clear();



                }else if(response.code()== 201 ){
                    Toast.makeText(MainActivity.this,"Password does not match ",Toast.LENGTH_SHORT).show();
                }else if(response.code()== 202 ){
                    Toast.makeText(MainActivity.this,"no user found",Toast.LENGTH_SHORT).show();
                }else if (response.code() == 203){
                    Toast.makeText(MainActivity.this,"Account is not verified",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MailVerificationRegisterActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("firstname",email);
                    intent.putExtra("lastname",email);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void login() {
        newAccount = (TextView) findViewById(R.id.id_newAccount);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    /* public Customer onResponse(String response) {
         JSONObject jsonObject = null;
         try {
             jsonObject = new JSONObject(response);

             Customer customer= gson.fromJson(jsonObject.getJSONObject("connected").toString(),Customer.class);
             return customer;
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return customer;
     }*/
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

