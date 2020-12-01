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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;


public class Profile extends Fragment {

    Button btn1 ;
    TextView firstName,email;
    ImageView avatar ;
    String pathImage="https://polar-peak-71928.herokuapp.com/uploads/users/";
    String avatarName;
    private SharedPreferences sp ;
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