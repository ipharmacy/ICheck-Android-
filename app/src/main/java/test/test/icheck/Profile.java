package test.test.icheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;


public class Profile extends Fragment {

    Button btn1 ;
    TextView firstName,lastName,email;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        btn1 = (Button)v.findViewById(R.id.button2);
        firstName = (TextView)v.findViewById(R.id.textView);
        lastName  = (TextView)v.findViewById(R.id.textView3);
        email = (TextView)v.findViewById(R.id.textView);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionDestroy(v);
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
             }
        });
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