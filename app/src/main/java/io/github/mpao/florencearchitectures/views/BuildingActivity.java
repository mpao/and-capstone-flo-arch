package io.github.mpao.florencearchitectures.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.databinding.ActivityBuildingBinding;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.views.adapters.ImagesAdapter;

public class BuildingActivity extends AppCompatActivity {

    private ActivityBuildingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_building);
        Building building = getIntent().getParcelableExtra(App.INTENT_BUILDING);
        setUpToolbar(building);
        binding.content.setBuilding(building);

        if(building == null){
            // on error, all the views will be set to invisible and error will be showed
            binding.fab.setVisibility(View.GONE);
            Toast.makeText(this, R.string.building_error, Toast.LENGTH_LONG).show();
        } else {
            // bind images and toolbar title. The other attributes are binded in the views
            setUpToolbar(building);
            binding.content.imagesPreview.hasFixedSize();
            binding.content.imagesPreview.setAdapter(
                    new ImagesAdapter(this, building.getImages())
            );
            binding.content.imagesPreview.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            );
        }

        binding.fab.setOnClickListener( view ->
            Toast.makeText(this, "TBD", Toast.LENGTH_SHORT).show()
        );

    }

    /*
     * set up the toolbar
     */
    private void setUpToolbar(Building building){

        setSupportActionBar( binding.toolbar );
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(building != null) {
            binding.toolbar.setTitle(building.getName());
            Picasso.get().load(building.getMainImage()).placeholder(R.mipmap.placeholder).into(binding.mainImage);
        }

    }

}
