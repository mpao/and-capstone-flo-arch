package io.github.mpao.florencearchitectures.views;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import io.github.mpao.florencearchitectures.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private FusedLocationProviderClient clientPosition;
    private Activity activity;
    private static final int DEFAULT_ZOOM = 14;
    private static final String MAP_SAVE = "map_save";
    private static final int LOCATION_REQUEST_CODE = 42;
    private static final LatLng DEFAULT_POS = new LatLng(43.7652256,11.2477555);

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
                // Set the map's camera position to the current location of the device.
                Location location = task.getResult();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
                map.setMyLocationEnabled(true);
                //todo where is my blu dot ?
            } else {
                this.setDefaultPosition();
            }
        });

    }

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
