package uk.co.jatra.scrollrecyclerchild;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by tim on 24/10/2015.
 */
public class Place implements Titled {
    private LatLng latLng;
    private String name;
    private String snippet;

    public Place(String name, double lat, double lng, String snippet) {
        this.name = name;
        this.snippet = snippet;
        latLng = new LatLng(lat, lng);
    }

    public Marker placeOnMap(GoogleMap map) {
        return map.addMarker(new MarkerOptions()
                .title(name)
                .snippet(snippet)
                .position(latLng));
    }

    public String getTitle() {
        return name;
    }

}
