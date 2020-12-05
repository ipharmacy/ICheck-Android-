package test.test.icheck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import io.reactivex.subjects.PublishSubject;
import test.test.icheck.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private ArrayList<String> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryText;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.categoryText = (TextView) itemView.findViewById(R.id.id_cv_categoryname);
        }
    }

    public final PublishSubject<String> onClickSubject = PublishSubject.create();

    public CategoryAdapter(ArrayList<String> data) {
        this.dataSet = data;
    }

    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rc_categories, parent, false);


        CategoryAdapter.MyViewHolder myViewHolder = new CategoryAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.MyViewHolder holder, final int listPosition) {
        final String element =  dataSet.get(listPosition);
        TextView categoryText = holder.categoryText;
      //  friendImage.setImageResource(dataSet.get(listPosition).getFriendImage());
        String category =dataSet.get(listPosition);
        categoryText.setText(category);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(element);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
