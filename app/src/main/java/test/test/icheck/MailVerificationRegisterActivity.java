package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.entity.Customer;

public class MailVerificationRegisterActivity extends AppCompatActivity {
String email,firstname,lastname;
IMyService iMyService;
EditText inputcode1,inputcode2,inputcode3,inputcode4,inputcode5,inputcode6;
String verifCode;
Button btnSend;
String  verificationCode;
    public static final String FILE_NAME = "test.test.icheck.shared";
    private SharedPreferences Preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_verification_register);
        Preferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        inputcode1 = (EditText)findViewById(R.id.input1);
        inputcode2 = (EditText)findViewById(R.id.input2);
        inputcode3 = (EditText)findViewById(R.id.input3);
        inputcode4 = (EditText)findViewById(R.id.input4);
        inputcode5 = (EditText)findViewById(R.id.input5);
        inputcode6 = (EditText)findViewById(R.id.input6);
        btnSend = (Button)findViewById(R.id.verificationSend);
        Bundle extras = getIntent().getExtras();
        email= extras.getString("email");
        firstname = extras.getString("firstname");
        lastname = extras.getString("lastname");
        sendVerificationCode();
        setupInput();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputcode1 !=null && inputcode2 !=null && inputcode3 !=null && inputcode4 !=null && inputcode5 !=null && inputcode6 !=null ){
                    verifCode = inputcode1.getText().toString()+inputcode2.getText().toString()+inputcode3.getText().toString()+inputcode4.getText().toString()+inputcode5.getText().toString()+inputcode6.getText().toString();
                    if (verifCode.equals(verificationCode)){
                        System.out.println("Succes");
                        verifyAccount();
                    }
                }
            }
        });
    }

    private void verifyAccount() {
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        Call<Customer> called = iMyService.verifyAccount(map);
       called.enqueue(new Callback<Customer>() {
           @Override
           public void onResponse(Call<Customer> call, Response<Customer> response) {
               Customer result = response.body();
               SharedPreferences.Editor editor = Preferences.edit();
               editor.putString("email",result.getEmail());
               editor.putString("firstName",result.getFirstName());
               editor.putString("lastName",result.getLastName());
               editor.putString("phone",result.getPhone());
               editor.putString("sexe",result.getSexe());
               editor.putString("avatar",result.getAvatar());
               editor.putString("userId",result.getId());
               editor.apply();
               System.out.println( "Email back"+result.getEmail()+result.getFirstName()+" IDD MAWJOUD : "+result.getId());
               Toast.makeText(MailVerificationRegisterActivity.this, "Succes " + result, Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(MailVerificationRegisterActivity.this, HomeActivity.class);
               startActivity(intent);
           }

           @Override
           public void onFailure(Call<Customer> call, Throwable t) {

           }
       });
    }

    private void sendVerificationCode() {
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        verificationCode = getVerificationCode();
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("name",firstname+" "+lastname);
        map.put("verificationCode",verificationCode);
        Call called = iMyService.sendVerificationCode(map);
        called.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("Mail send : verification code : "+verificationCode +" "+response.body() );
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("failed ");
            }
        });
    }

    private String getVerificationCode() {
        String verif;
       int x = (int) (Math.random() * (999999 - 100000)+ 100000);
       verif = String.valueOf(x);
        return verif;
    }
    public void setupInput(){
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}