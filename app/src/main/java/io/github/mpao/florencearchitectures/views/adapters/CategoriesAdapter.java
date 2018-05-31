package io.github.mpao.florencearchitectures.views.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.github.mpao.florencearchitectures.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{

    private Cursor cursor;
    private Context context;

    public CategoriesAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor  = cursor;
    }

    @Override
    @NonNull
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.category_row, parent, false);
        return new ViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder viewHolder, int position) {

        cursor.moveToPosition(position);
        viewHolder.textView.setText(cursor.getString(0));
        Picasso.get().load(cursor.getString(1)).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {

        return cursor.getCount();

    }

    /*
    ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        private ViewHolder(View view){
            super(view);
            textView  = view.findViewById(R.id.cat_name);
            imageView = view.findViewById(R.id.cat_image);
        }

    }

}
