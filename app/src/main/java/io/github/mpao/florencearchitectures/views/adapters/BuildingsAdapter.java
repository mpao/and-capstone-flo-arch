package io.github.mpao.florencearchitectures.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.List;
import io.github.mpao.florencearchitectures.databinding.ListRowBinding;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.views.BuildingActivity;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.ViewHolder> {

    private List<Building> list;
    private Context context;

    public BuildingsAdapter(Context context, List<Building> list){
        this.context = context;
        this.list    = list;
    }

    @Override
    @NonNull
    public BuildingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
        ListRowBinding bind = ListRowBinding.inflate( layoutInflater, parent, false);
        return new ViewHolder( bind );

    }

    @Override
    public void onBindViewHolder(@NonNull BuildingsAdapter.ViewHolder viewHolder, int position) {

        viewHolder.bind( list.get(position) );

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    /*
     * ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ListRowBinding bind;

        private ViewHolder( ListRowBinding binding ){

            super( binding.getRoot() );
            this.bind = binding;

        }

        @SuppressWarnings("unchecked")
        public void bind(Building building){

            bind.setBuilding(building);
            Picasso.get().load( building.getMainImage() ).into(bind.buildingImage);
            bind.buildingImage.setOnClickListener( view ->{
                Intent intent = new Intent(context, BuildingActivity.class);
                intent.putExtra(App.INTENT_BUILDING, building);
                Pair<View, String> image = Pair.create(bind.buildingImage, "main_image");
                Pair<View, String> title = Pair.create(bind.buildingName, "title_name");
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity)context, image, title);
                context.startActivity(intent, options.toBundle());
            });
            bind.executePendingBindings();

        }

    }

}
