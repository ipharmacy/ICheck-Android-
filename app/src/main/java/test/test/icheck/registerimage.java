package test.test.icheck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

import static android.os.Environment.getExternalStoragePublicDirectory;

public class registerimage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button btn,update,upload;
    ImageView image;
    Bitmap mBitmap;
    IMyService iMyService;
    String pathToFile;
    String imageName = "vide";
    String email = "";
    String picturePath;
    private static final int PERMISSION_REQUEST=0;
    private static final int RESULT_LOAD_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerimage);
        Bundle extras = getIntent().getExtras();
        email= extras.getString("email");
        System.out.println("email : ++ "+email);
        btn = (Button)findViewById(R.id.id_buttonUpdateAvatar);
        update = (Button)findViewById(R.id.id_updateAvatar) ;
        image = (ImageView)findViewById(R.id.id_imageAvatar);
        upload = (Button)findViewById(R.id.id_uploadImageServer);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  dispatchPictureTakerAction();
                Intent i =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void updateUser() {
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("avatar", imageName);
        Call called = iMyService.updateAvatar(map);
        called.enqueue(new Callback() {


            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(registerimage.this, "User added succesfuly ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(registerimage.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(registerimage.this, "Failed to add ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage() {
        File file = new File(picturePath);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/"),file);
        imageName = file.getName();
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload",file.getName(),requestBody);
        RequestBody data = RequestBody.create(MediaType.parse("text/plain"),"upload");
        Retrofit retrofit = RetrofitClient.getInstance();
        IMyService uploadImage = retrofit.create(IMyService.class);
        Call call = uploadImage.postImage(part , data);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 200) {
                    System.out.println("Uploaded " +response.body());
                    Toast.makeText(registerimage.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(registerimage.this, "Image Failed", Toast.LENGTH_SHORT).show();
            }
        });
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
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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

   /* private void dispatchPictureTakerAction() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null ){
            File photoFile = null;
            photoFile = createPhotoFile();
            if(photoFile != null ){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(registerimage.this,"test.test.icheck.fileprovider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePic,1) ;
            }



        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }*/

}