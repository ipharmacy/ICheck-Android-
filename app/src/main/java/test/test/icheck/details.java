package test.test.icheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;


import test.test.icheck.adapter.photoAdapter;
import test.test.icheck.entity.photoProduct;
//import test.test.icheck.adapter.reviewAdapter;
import test.test.icheck.entity.reviews;


public class details extends AppCompatActivity {
    ArrayList<photoProduct> photoList;
    ArrayList<reviews> reviewList;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static photoAdapter adapter;
   // private static reviewAdapter adapter2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
     //   createFriendListView();
       // creatReviewListView();
    }
   /* public void createFriendListView(){
        photoProduct f1 = new photoProduct(R.drawable.nikeair,"nike");
        photoList = new ArrayList<photoProduct>();
        photoList.add(f1);
        photoList.add(f1);
        photoList.add(f1);
        photoList.add(f1);
        photoList.add(f1);
        photoList.add(f1);

        adapter = new photoAdapter(photoList);
        recyclerView = (RecyclerView)findViewById(R.id.id_listPhotos);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    public void creatReviewListView(){
         reviews f1 = new reviews(R.drawable.dhialogo,5,"Dhia Ben Hamouda","I liked that they arrived promptly and they were a perfect fit");
        reviewList = new ArrayList<reviews>();
        reviewList.add(f1);
        reviewList.add(f1);
        reviewList.add(f1);
        adapter2 = new reviewAdapter(reviewList);
        recyclerView = (RecyclerView)findViewById(R.id.id_listReviews);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter2);
    }
*/
}