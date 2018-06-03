package io.github.mpao.florencearchitectures.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import io.github.mpao.florencearchitectures.R;

public class BuildingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show()
        );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String url = getIntent().getStringExtra("main_image");
        String id  = getIntent().getStringExtra(BuildingActivity.class.toString());

        ImageView image = findViewById(R.id.main_image);
        Picasso.get().load(url).placeholder(R.drawable.image_placeholder).into(image);

    }

}
