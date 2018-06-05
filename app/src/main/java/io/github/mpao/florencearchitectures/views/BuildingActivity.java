package io.github.mpao.florencearchitectures.views;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.entities.CommonTags;
import io.github.mpao.florencearchitectures.models.databases.AppContract;
import io.github.mpao.florencearchitectures.views.adapters.ImagesAdapter;

public class BuildingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView category, name, author, year, typology, address, description;
    private static final String BUILDING_ID = "id";
    private static final String BUILDING_SAVE = "save_building";
    private Building building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        category    = findViewById(R.id.building_category);
        name        = findViewById(R.id.building_name);
        author      = findViewById(R.id.building_author);
        year        = findViewById(R.id.building_year);
        typology    = findViewById(R.id.building_typology);
        address     = findViewById(R.id.building_address);
        description = findViewById(R.id.building_description);

        if(savedInstanceState != null){
            building = savedInstanceState.getParcelable(BUILDING_SAVE);
        }
        // setup toolbar:
        // main image and title come with intent for a quick bind
        // while the entire Building object will come from loader
        String mainImage = getIntent().getStringExtra(CommonTags.IMAGE);
        String title     = getIntent().getStringExtra(CommonTags.TITLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar()!= null;
        getSupportActionBar().setTitle(title);
        ImageView image = findViewById(R.id.main_image);
        Picasso.get().load(mainImage).placeholder(R.drawable.image_placeholder).into(image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // fab
        this.setUpFab();
        // loader
        Bundle args = new Bundle();
        args.putString(BUILDING_ID, getIntent().getStringExtra(CommonTags.ID));
        getLoaderManager().initLoader(1, args, this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUILDING_SAVE, building);
    }

    //region Loader methods
    @NonNull
    @Override
    public CursorLoader onCreateLoader(int id, Bundle args) {

        String buildingId = args.getString(BUILDING_ID);
        return new CursorLoader(
                this,
                AppContract.AppContractElement.CONTENT_URI.buildUpon().appendPath(buildingId).build(),
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor cursor) {

        while (cursor.moveToNext()){
            building = new Building(cursor);
        }
        cursor.close();
        this.bindBuilding();
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
    //endregion

    private void bindBuilding(){

        if(building != null) {
            category.setText(building.getPeriod());
            name.setText(building.getName());
            author.setText(building.getAuthor());
            year.setText(building.getYear());
            typology.setText(building.getTypology());
            address.setText(building.getAddress());
            description.setText(building.getDescription());

            ImagesAdapter adapter = new ImagesAdapter(this, building.getImages());
            RecyclerView recyclerView = findViewById(R.id.images_preview);
            recyclerView.hasFixedSize();
            recyclerView.setAdapter(adapter);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(manager);

        }else{
            findViewById(R.id.category_wrapper).setVisibility(View.INVISIBLE);
            findViewById(R.id.information_wrapper).setVisibility(View.INVISIBLE);
            findViewById(R.id.description_wrapper).setVisibility(View.INVISIBLE);
            findViewById(R.id.images_wrapper).setVisibility(View.INVISIBLE);
            findViewById(R.id.error_image).setVisibility(View.VISIBLE);
            Snackbar.make(findViewById(R.id.layout), R.string.building_error, Snackbar.LENGTH_LONG).show();
        }
    }

    private void setUpFab(){

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show()
        );

    }

}
