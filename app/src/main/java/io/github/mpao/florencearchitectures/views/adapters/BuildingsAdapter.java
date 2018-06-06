package io.github.mpao.florencearchitectures.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.List;
import io.github.mpao.florencearchitectures.databinding.ListRowBinding;
import io.github.mpao.florencearchitectures.entities.Building;

public class BuildingsAdapter extends RecyclerView.Adapter<BuildingsAdapter.ViewHolder> {

    private List<Building> list;

    public BuildingsAdapter(List<Building> list){
        this.list  = list;
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

        public void bind(Building building){

            bind.setBuilding(building);
            Picasso.get().load( building.getMainImage() ).into(bind.buildingImage);
            bind.executePendingBindings();

        }

    }

}
