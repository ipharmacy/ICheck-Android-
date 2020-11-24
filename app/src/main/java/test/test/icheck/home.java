package test.test.icheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {


    private BottomNavigationView navMenu;
    private SharedPreferences sp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        sp = getSharedPreferences(MainActivity.FILE_NAME,MODE_PRIVATE);

        if (!sp.getString("email","").isEmpty()){
            System.out.println(sp.getString("email",""));
        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Fcontainer,new HomeFragment())
                .commit();


        navMenu = findViewById(R.id.bottomAppBar);
        navMenu.setOnNavigationItemSelectedListener(navListener);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.HomePage:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.FavorisPage:
                            selectedFragment = new FavorisFragment();
                            break;
                        case R.id.NotifyPage:
                            selectedFragment = new Notifications();
                            break;
                        case R.id.ProfilePage:
                            selectedFragment = new Profile();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Fcontainer,selectedFragment)
                            .commit();
                    return true;
                }
            };
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}