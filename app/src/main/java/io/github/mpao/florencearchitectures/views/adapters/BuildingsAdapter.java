package io.github.mpao.florencearchitectures.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.entities.CommonTags;
import io.github.mpao.florencearchitectures.views.BuildingActivity;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;

    public BuildingsAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor  = cursor;
    }

    @Override
    @NonNull
    public BuildingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.list_row, parent, false);
        return new ViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull BuildingsAdapter.ViewHolder viewHolder, int position) {

        cursor.moveToPosition(position);
        Building building = new Building(cursor);
        viewHolder.textView.setText(building.getName());
        Picasso.get().load(building.getMainImage()).into(viewHolder.imageView);
        viewHolder.layout.setOnClickListener( element -> {
            Intent intent   = new Intent(context, BuildingActivity.class);
            intent.putExtra(CommonTags.ID, building.getName() );
            intent.putExtra(CommonTags.TITLE, building.getName());
            intent.putExtra(CommonTags.IMAGE, building.getMainImage());
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) context, viewHolder.imageView, "main_image");
            context.startActivity(intent, options.toBundle());
        });

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
        View layout;

        private ViewHolder(View view){
            super(view);
            textView  = view.findViewById(R.id.building_name);
            imageView = view.findViewById(R.id.building_image);
            layout    = view.findViewById(R.id.building_layout);
        }

    }

}
