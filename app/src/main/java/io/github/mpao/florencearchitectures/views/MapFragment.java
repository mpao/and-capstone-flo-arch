package io.github.mpao.florencearchitectures.views;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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
import java.util.Objects;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.viewmodels.BuildingsListViewModel;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private FusedLocationProviderClient clientPosition;
    private Activity activity;
    BuildingsListViewModel viewModel;
    private static final int DEFAULT_ZOOM = 14;
    private static final int RADIUS_POS = 1000;
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
        mapView.onCreate(null);
        mapView.getMapAsync(this);
        //todo map position on rotation
        // List fragment and Map fragment share the same data model, the complete list
        // of element: in this case I can pass the activity as LyfeCycleOwner to share
        // the same ViewModel and avoid database calls when I change between those two fragments
        // https://developer.android.com/topic/libraries/architecture/viewmodel#sharing
        FragmentActivity activity = Objects.requireNonNull(getActivity());
        viewModel = ViewModelProviders.of(activity).get(BuildingsListViewModel.class);
        return root;

    }

    private void putDataMarkersOnMap(Building[] buildings){
        // prepare custom InfoWindow
        map.setInfoWindowAdapter(new MapInfoWindow(activity));
        for (Building building : buildings) {
            // put data into marker
            LatLng here  = new LatLng(building.getLatitude(), building.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(here)
                    .title(building.getName())
                    .snippet(building.getMainImage())
            ).setTag(building);
            // set up click listener
            map.setOnInfoWindowClickListener(marker ->{
                View infoWindow   = View.inflate(activity, R.layout.map_building_info, null);   // get infoWindow's layout
                ImageView preview = infoWindow.findViewById(R.id.image);                             // get preview image
                Intent intent     = new Intent(activity, BuildingActivity.class);                    // create intent
                intent.putExtra(App.INTENT_BUILDING, (Building)marker.getTag());
                // todo QUESTION: Shared element between InfoWindow and activity. Will it works ? seems yes, but it's horrible :|
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, preview, "main_image");
                activity.startActivity(intent, options.toBundle());
            });
        }
    }

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
        viewModel.getList().observe(this, list ->{
            if( list != null) {
                this.putDataMarkersOnMap(list);
            }
        });

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
    public void onStart() {
        super.onStart();
        if(mapView != null) mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mapView != null) mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mapView != null) mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView != null) mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapView != null) mapView.onLowMemory();
    }
    //endregion

}
