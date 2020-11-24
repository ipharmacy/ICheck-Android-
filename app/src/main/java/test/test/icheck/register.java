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

import org.w3c.dom.Text;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;

public class register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PERMISSION_REQUEST=0;
    private static final int RESULT_LOAD_IMAGE=1;

    TextView login;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    ImageView upload;
    Button btn;
    private SharedPreferences sp ;
    public static final String FILE_NAME = "test.test.icheck.shared";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        upload = (ImageView)findViewById(R.id.imageView1);
        btn = (Button)findViewById(R.id.button3);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("cloicked");
                Intent i =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });

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
               // Intent intent = new Intent(register.this,MainActivity.class);
                //startActivity(intent);
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
                compositeDisposable.add( iMyService.register(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString(),"55340453","Homme")
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String response) throws Exception {
                                sessionDestroy();
                                Toast.makeText(register.this,""+response,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(register.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                );



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






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri SelectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(SelectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    upload.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    System.out.println("path : "+picturePath);
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}