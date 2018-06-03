package io.github.mpao.florencearchitectures.views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.models.databases.AppContract;

public class MapFragment extends Fragment implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {

    private MapView mapView;
    private GoogleMap map;
    private FusedLocationProviderClient clientPosition;
    private Activity activity;
    private static final int DEFAULT_ZOOM = 14;
    private static final int RADIUS_POS = 1000;
    private static final String MAP_SAVE = "map_save";
    private static final int LOCATION_REQUEST_CODE = 42;
    private static final LatLng DEFAULT_POS = new LatLng(43.766449,11.2471021); // S.Spirito square, Florence

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        activity = getActivity();
        // MapView stuff
        mapView = root.findViewById(R.id.mapView);
        clientPosition = LocationServices.getFusedLocationProviderClient(activity);

        Bundle mapSave = null;
        if(savedInstanceState != null){
            mapSave = savedInstanceState.getBundle(MAP_SAVE);
        }
        mapView.onCreate(mapSave);
        mapView.getMapAsync(this);

        // loader
        getLoaderManager().initLoader(1, null, this);

        return root;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {

        super.onSaveInstanceState(state);
        Bundle mapSave = state.getBundle(MAP_SAVE);
        if (mapSave == null) {
            mapSave = new Bundle();
            state.putBundle(MAP_SAVE, mapSave);
        }
        mapView.onSaveInstanceState(mapSave);

    }

    //region Loader stuff
    /*
     * get all the buildings from CONTENT_URI
     */
    @NonNull
    @Override
    public CursorLoader onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                activity,
                AppContract.AppContractElement.CONTENT_URI,
                null,
                null,
                null,
                null
        );

    }

    /*
     * draw marks on building's positions
     */
    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor cursor) {

        while (cursor.moveToNext()){
            //get data
            String name  = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.NAME));
            double lat   = cursor.getDouble(cursor.getColumnIndex(AppContract.AppContractElement.LATITUDE));
            double lon   = cursor.getDouble(cursor.getColumnIndex(AppContract.AppContractElement.LONGITUDE));
            String image = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.MAIN_IMAGE));
            LatLng here  = new LatLng(lat, lon);

            // prepare custom InfoWindow
            map.setInfoWindowAdapter(new MapInfoWindow(activity));
            // put data into marker
            map.addMarker(new MarkerOptions()
                    .position(here)
                    .title(name)
                    .snippet(image)
            );
            // set up click listener
            map.setOnInfoWindowClickListener(marker ->{
                View infoWindow = View.inflate(activity, R.layout.map_building_info, null); // get infoWindow's layout
                ImageView preview    = infoWindow.findViewById(R.id.image);                      // get preview image
                Intent intent   = new Intent(activity, BuildingActivity.class);                  // create intent
                intent.putExtra(BuildingActivity.class.getName(), marker.getTitle());            // put building ID
                intent.putExtra("main_image", marker.getSnippet());                        // put image usrl string
                // todo Shared element between InfoWindow and activity ? Will it works ?
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, preview, "main_image");
                startActivity(intent, options.toBundle());
                activity.startActivity(intent);
            });

        }
        cursor.close();

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
    //endregion

    //region Map, Permission and Location
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        this.requestPermissionAndSetPosition();
        map.setMinZoomPreference(DEFAULT_ZOOM);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);

        //LatLng here = new LatLng(43.7652256, 11.2477555);
        //map.addMarker(new MarkerOptions().position(here).title("Test me!"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(here));

    }

    /*
     * request permission: if already set, search for the last known position
     * and set it on the map, else ask for it
     */
    private void requestPermissionAndSetPosition(){

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.getDeviceLocation();
        } else {
            this.requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE
            );
        }

    }

    /*
     * If user dont allow geoloc, use a default position
     */
    private void setDefaultPosition() throws SecurityException{

        map.setMyLocationEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_POS));
        map.getUiSettings().setMyLocationButtonEnabled(false);

    }

    /*
     * Get the last know position of the user
     * Code from Google documentation
     * https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
     */
    private void getDeviceLocation() throws SecurityException{

        Task<Location> locationResult = clientPosition.getLastLocation();
        locationResult.addOnCompleteListener(activity,  task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM));
                map.setMyLocationEnabled(true);
                map.addCircle(new CircleOptions()
                        .center(position)
                        .radius(RADIUS_POS)
                        .strokeWidth(0)
                        .fillColor(ContextCompat.getColor(activity, R.color.position_radius)));
                // fixed: where is my blu dot ?
                // in emulator the blue dot doesnt appear in the middle of the circle,
                // on a real device instead, it works
            } else {
                this.setDefaultPosition();
            }
        });

    }

    /*
     * Callback of user permission question
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) throws SecurityException {

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.getDeviceLocation();
            } else {
                this.setDefaultPosition();
            }
        }

    }
    //endregion

    //region MapView unused methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    //endregion

}
