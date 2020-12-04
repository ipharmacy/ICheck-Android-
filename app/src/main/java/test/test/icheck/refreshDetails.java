package test.test.icheck;

import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

import test.test.icheck.entity.reviews;

public class refreshDetails extends DiffUtil.Callback {
    private ArrayList<reviews> oldList;
    private ArrayList<reviews> newList;

    public refreshDetails(ArrayList<reviews> oldList, ArrayList<reviews> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        return oldPosition== newPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        return newList.get(oldPosition) == newList.get(newPosition);
    }
}
