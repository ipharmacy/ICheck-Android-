package test.test.icheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;

public class BottomSheetReview  extends BottomSheetDialogFragment {
    EditText ReviewInput;
    RatingBar rateBar;
    Button submitRate;
    IMyService iMyService;
    public BottomSheetReview(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.row_add_item,container,false);
        ReviewInput = (EditText)view.findViewById(R.id.ReviewText) ;
        rateBar = (RatingBar)view.findViewById(R.id.BarRate);
        rateBar.setRating(5);
        submitRate = (Button)view.findViewById(R.id.OnSubmit);
        Bundle mArgs = getArguments();
        String prodId = mArgs.getString("prodId");
        String userId = mArgs.getString("userId");
        System.out.println( "PROD ID : "+prodId+" UserId "+ userId);
        submitRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rate = Float.toString(rateBar.getRating());
                final String review = ReviewInput.getText().toString();
                if (ReviewInput.getText().toString().isEmpty()){
                    Toast.makeText(BottomSheetReview.super.getContext(),"Please write a review ",Toast.LENGTH_SHORT).show();
                }else {
                    Retrofit retrofitClient = RetrofitClient.getInstance();
                    iMyService = retrofitClient.create(IMyService.class);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("prodId",prodId);
                    map.put("review",review);
                    map.put("userId",userId);
                    map.put("rate",rate);
                    Call<HashMap<String,String>> call = iMyService.addReview(map);
                    call.enqueue(new Callback<HashMap<String, String>>() {
                        @Override
                        public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                            //  Toast.makeText(ProductDetailActivity.this, "Sucess  "+response.body().get("message"), Toast.LENGTH_SHORT).show();
                            //System.out.println("reviewList : "+reviewList);
                            Intent intent = new Intent(getActivity(),ProductDetailActivity.class);
                            intent.putExtra("productId",prodId);
                            intent.putExtra("review",review);
                            intent.putExtra("rate",rate);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                            // Toast.makeText(ProductDetailActivity.this, "FAIL ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        return view;
    }
}
