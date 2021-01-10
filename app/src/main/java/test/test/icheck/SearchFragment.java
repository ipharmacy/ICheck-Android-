package test.test.icheck;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.FriendsAdapter;
import test.test.icheck.adapter.SearchUserAdapter;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.Product;

public class SearchFragment extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static SearchUserAdapter adapterf;
    SearchView searchView;
    IMyService iMyService;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("inside fragment");

        View v =  inflater.inflate(R.layout.fragment_search, container, false);
        searchView = (SearchView)v.findViewById(R.id.id_searchUserss);

        createFriendListView(v);

        return v;
    }
    public void createFriendListView(View v) {
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        Call<ArrayList<Customer>> call = iMyService.getAllCustomers();
        call.enqueue(new Callback<ArrayList<Customer>>() {
            @Override
            public void onResponse(Call<ArrayList<Customer>> call, Response<ArrayList<Customer>> response) {
                ArrayList<Customer> allUsers = new ArrayList<>();
                allUsers = response.body();
                adapterf = new SearchUserAdapter(allUsers,getContext());
                recyclerView = (RecyclerView) v.findViewById(R.id.id_search_userrv);
                layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapterf);
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapterf.getFilter().filter(newText);
                        return false;
                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<Customer>> call, Throwable t) {

            }
        });
    }
    private void filtreCategory(List<String> categoryList, List<Product> products) {
        for(int i=0;i<products.size();i++){
            if (categoryList.isEmpty()){
                categoryList.add(products.get(i).getCategory());
            }else{
                if(!(categoryList.contains(products.get(i).getCategory()))){
                    categoryList.add(products.get(i).getCategory());
                }
            }
        }
    }

}