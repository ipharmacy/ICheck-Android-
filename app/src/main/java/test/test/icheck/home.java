package test.test.icheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import test.test.icheck.adapter.friendsAdapter;
import test.test.icheck.adapter.productAdapter;
import test.test.icheck.entity.friends;
import test.test.icheck.entity.product;

public class home extends AppCompatActivity {


    private BottomNavigationView navMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

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

}