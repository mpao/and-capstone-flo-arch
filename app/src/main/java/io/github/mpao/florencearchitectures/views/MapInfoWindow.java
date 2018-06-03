package io.github.mpao.florencearchitectures.views;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import io.github.mpao.florencearchitectures.R;

public class MapInfoWindow implements GoogleMap.InfoWindowAdapter {

    private Activity activity;

    public MapInfoWindow(Activity activity){
        this.activity = activity;
    }

    /*
     * https://developers.google.com/maps/documentation/android-sdk/infowindows
     * if null is returned, it will then call getInfoContents(Marker).
     * If this also returns null, then the default info window will be used.
     */
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = View.inflate(activity, R.layout.map_building_info, null);
        TextView title  = view.findViewById(R.id.title);
        ImageView image = view.findViewById(R.id.image);

        image.setContentDescription( marker.getTitle() );
        title.setText( marker.getTitle() );
        Picasso.get()
                .load( marker.getSnippet() )
                .placeholder(R.mipmap.placeholder)
                .resize(R.dimen.image_preview_width, R.dimen.image_preview_height)
                .onlyScaleDown()
                .centerCrop()
                .into(image, new MarkerCallback(marker));

        return view;

    }

    // Callback is an interface from Picasso:
    // https://gist.github.com/nissivm/c658839631906c314b381078b4735c9a
    // problem with InfoWindowAdapter and Picasso
    static class MarkerCallback implements Callback {
        Marker marker;

        MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onError(Exception e) { }

        @Override
        public void onSuccess() {
            if (marker == null) {
                return;
            }

            if (!marker.isInfoWindowShown()) {
                return;
            }

            marker.hideInfoWindow(); // Calling only showInfoWindow() throws an error
            marker.showInfoWindow();
        }

    }

}
