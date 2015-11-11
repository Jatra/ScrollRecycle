package uk.co.jatra.scrollrecyclerchild;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

public class CollapsingHeaderActivity extends AppCompatActivity {

    private static final String TAG = CollapsingHeaderActivity.class.getSimpleName();
    private MapView mapView;
    private Map<String, Place> placeByName;
    private Map<String, Marker> markerByName;
    static final Place[] PLACES = new Place[]{
        new Place("Perth", -31.90, 115.86, "Eastern Australia"),
        new Place("Boston", 42.3601, -71.0589, "Eastern USA"),
        new Place("London", 51.5072, 0.1275, "United Kingom"),
        new Place("Falmer", 50.8603529,  -.084474, "Home of the Seagulls"),
        new Place("Timbuktu", 16.7758, 3.0094, "Nice name"),
        new Place("Camberley", 51.3350, -0.7420, "why?"),
    };
    private TabLayout tabLayout;
    private Handler handler;
    private Marker selectedMarker;
    private Boolean zoomAfterMove = Boolean.TRUE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        MapsInitializer.initialize(this);
        setContentView(R.layout.collapsing_header_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Locations");

        mapView = createMap(savedInstanceState);
        FrameLayout header = (FrameLayout) findViewById(R.id.header);
        header.addView(mapView);

        placeByName = new HashMap<>();
        for (int i=0; i<PLACES.length; i++) {
            placeByName.put(PLACES[i].getTitle(), PLACES[i]);
        }

        ViewPager viewPager =setupPager();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String selected = tab.getText().toString();
                Marker marker = markerByName.get(selected);
                selectMarker(marker);
                final LatLng markerPosition = marker.getPosition();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        animateToPosition(markerPosition);
                    }
                });
//                final CameraPosition cameraPosition = CameraPosition.builder()
//                        .target(markerPosition)
//                        .build();
//                mapView.getMapAsync(new OnMapReadyCallback() {
//                    @Override
//                    public void onMapReady(GoogleMap googleMap) {
//                        if (mapView.getMap() != null) {
//                            CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
//                            googleMap.animateCamera(update);
//                        }
//                    }
//                });
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //        addFab();

    }

    private ViewPager setupPager() {

        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);

        FragmentStatePagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), PLACES);
        pager.setAdapter(adapter);

        return pager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private MapView createMap(Bundle savedInstanceState) {
        GoogleMapOptions options = new GoogleMapOptions()
                .zoomControlsEnabled(true)
                .compassEnabled(true)
                .mapToolbarEnabled(true)
                ;
        MapView mapView = new MapView(this, options);
        mapView.onCreate(savedInstanceState);
        mapView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "map ready");
                googleMap.setMyLocationEnabled(true);
                addMarkers(googleMap);
            }
        });
        return mapView;
    }

    private void addMarkers(GoogleMap googleMap) {
        int numPlaces = PLACES.length;
        markerByName = new HashMap<>();
        for (int i=0; i<numPlaces; i++) {
            Marker marker = PLACES[i].placeOnMap(googleMap);
            markerByName.put(PLACES[i].getTitle(), marker);
        }
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "Maker clicked: " + marker.getTitle() + " " + marker.getId() + " " + marker.getSnippet());
                zoomAfterMove = Boolean.FALSE;
                selectMarker(marker);
                String selectedTitle = marker.getTitle();
                int tabCount = tabLayout.getTabCount();
                for (int i = 0; i < tabCount; i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    if (selectedTitle.equals(tab.getText())) {
                        tab.select();
                    }
                }
                return false;
            }
        });
    }

    private void selectMarker(Marker marker) {
        if (selectedMarker != null) {
            setMarkerColour(selectedMarker, BitmapDescriptorFactory.HUE_RED);
            selectedMarker.hideInfoWindow();
        }
        selectedMarker = marker;
        setMarkerColour(selectedMarker, BitmapDescriptorFactory.HUE_CYAN);
        selectedMarker.showInfoWindow();
    }

    private void setMarkerColour(Marker marker, float hue) {
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(hue));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private synchronized void animateToPosition(LatLng position) {
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(position)
                .zoom(14)
                .build();
        final CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                googleMap.animateCamera(update, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        if (zoomAfterMove.booleanValue()) {
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(10f);
                            googleMap.animateCamera(zoom, 2000, null);
                        }
                        zoomAfterMove = Boolean.TRUE;
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
    }

    //    private void addFab() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
}
