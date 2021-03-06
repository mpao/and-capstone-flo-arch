package io.github.mpao.florencearchitectures.views.custom;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class StaggedGridItemDecoration extends RecyclerView.ItemDecoration {

    private int padding;
    private int gridSize;

    public StaggedGridItemDecoration(Context context, int dimenMarginResource, int gridSize) {

        padding = context.getResources().getDimensionPixelSize(dimenMarginResource);
        this.gridSize = gridSize;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams)view .getLayoutParams();
        int column = lp.getSpanIndex();

        if( gridSize == 1){
            // colonna singola
            outRect.top    = 0;
            outRect.bottom = padding;
            outRect.left   = 0;
            outRect.right  = 0;
            return;
        }

        if( column == 0 ){
            // prima colonna
            outRect.top    = 0;
            outRect.bottom = padding;
            outRect.left   = 0;
            outRect.right  = padding/2;
        }else if( column != gridSize-1){
            // colonne in mezzo
            outRect.top    = 0;
            outRect.bottom = padding;
            outRect.left   = padding/2;
            outRect.right  = padding/2;
        }else{
            // ultima colonna
            outRect.top    = 0;
            outRect.bottom = padding;
            outRect.left   = padding/2;
            outRect.right  = 0;
        }

    }

}

