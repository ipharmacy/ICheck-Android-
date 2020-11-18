package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

import test.test.icheck.adapter.friendsAdapter;
import test.test.icheck.adapter.productAdapter;
import test.test.icheck.entity.friends;
import test.test.icheck.entity.product;

public class home extends AppCompatActivity {
    private static productAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<product> productList;
    private static friendsAdapter adapterf;
    private static ArrayList<friends> friendsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        createProductListView();
        createFriendListView();


    }
    public void createProductListView(){
        product p1 = new product(2,R.drawable.nikeair,R.drawable.nikelogo,"Nike Air Force 1","nike","Hay echaaeb nabeul","Available");
        product p2 = new product(4,R.drawable.nikeair,R.drawable.nikelogo,"Nike Air Force 2","Addidas","Hay echaaeb nabeul","Unavailable");
        productList = new ArrayList<product>();
        productList.add(p1);
        productList.add(p2);
        adapter = new productAdapter(productList);
        recyclerView = (RecyclerView)findViewById(R.id.id_listProcuts);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    public void createFriendListView(){
        friends f1 = new friends(R.drawable.dhialogo,"nike");
        friendsList = new ArrayList<friends>();
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);
        friendsList.add(f1);

        adapterf = new friendsAdapter(friendsList);
        recyclerView = (RecyclerView)findViewById(R.id.id_listFriends);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterf);
    }
}