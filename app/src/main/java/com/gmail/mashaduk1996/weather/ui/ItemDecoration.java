package com.gmail.mashaduk1996.weather.ui;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public ItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 2) {

            outRect.right = offset;
            outRect.left = offset;
            outRect.top = offset;
            outRect.bottom = offset;
        }
    }
}
