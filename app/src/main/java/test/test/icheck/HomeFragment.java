package test.test.icheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import test.test.icheck.adapter.friendsAdapter;
import test.test.icheck.adapter.productAdapter;
import test.test.icheck.entity.friends;
import test.test.icheck.entity.product;


public class HomeFragment extends Fragment {
    private static productAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<product> productList;
    private static friendsAdapter adapterf;
    private static ArrayList<friends> friendsList;
    private SharedPreferences sp ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        createProductListView(v);
        createFriendListView(v);

        return v;
    }



    public void createProductListView(View v){
        product p1 = new product(2, R.drawable.nikeair, R.drawable.nikelogo,"Nike Air Force 1","nike","Hay echaaeb nabeul","Available");
        product p2 = new product(4, R.drawable.nikeair, R.drawable.nikelogo,"Nike Air Force 2","Addidas","Hay echaaeb nabeul","Unavailable");
        productList = new ArrayList<product>();
        productList.add(p1);
        productList.add(p2);
        adapter = new productAdapter(productList);
        recyclerView = (RecyclerView) v.findViewById(R.id.id_listProcuts);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    public void createFriendListView(View v){
        friends f1 = new friends(R.drawable.dhialogo,"nike");
        friends f2 = new friends(R.drawable.youssef,"nike");
        friends f3 = new friends(R.drawable.eya,"nike");
        friends f4 = new friends(R.drawable.hamza,"nike");
        friends f5 = new friends(R.drawable.chekib,"nike");
        friends f6 = new friends(R.drawable.mbarki,"nike");

        friends f7 = new friends(R.drawable.salsabil,"nike");
        friends f8 = new friends(R.drawable.mehdi,"nike");

        friendsList = new ArrayList<friends>();
        friendsList.add(f1);
        friendsList.add(f2);
        friendsList.add(f3);
        friendsList.add(f4);
        friendsList.add(f5);
        friendsList.add(f6);

        friendsList.add(f7);
        friendsList.add(f8);

        adapterf = new friendsAdapter(friendsList);
        recyclerView = (RecyclerView) v.findViewById(R.id.id_listFriends);
        layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterf);
    }


}