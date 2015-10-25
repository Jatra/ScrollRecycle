package uk.co.jatra.scrollrecyclerchild;

import android.os.Bundle;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class CollapsingHeaderActivity extends AppCompatActivity {

    private static final String TAG = CollapsingHeaderActivity.class.getSimpleName();
    private MapView mapView;
    static final LatLng PERTH = new LatLng(-31.90, 115.86);
    static final Place[] PLACES = new Place[]{
        new Place("Perth", -31.90, 115.86, "Eastern Australia"),
        new Place("Boston", 42.3601, -71.0589, "Eastern USA"),
        new Place("London", 51.5072, 0.1275, "United Kingom"),
        new Place("Falmer", 50.8603529,  -.084474, "Home of the Seagulls"),
        new Place("Timbuktu", 16.7758, 3.0094, "Nice name"),
        new Place("Camberley", 51.3350, -0.7420, "why?"),

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsing_header_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Locations");

        mapView = createMap(savedInstanceState);
        FrameLayout header = (FrameLayout) findViewById(R.id.header);
        header.addView(mapView);

        setupPager();

        //        addFab();

    }

    private void setupPager() {

        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);

        FragmentStatePagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);
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
        MapView mapView = new MapView(this);
        mapView.onCreate(savedInstanceState);
        mapView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "map ready");
                addMarkers(googleMap);
            }
        });
        return mapView;
    }

    private void addMarkers(GoogleMap googleMap) {
        int numPlaces = PLACES.length;
        for (int i=0; i<numPlaces; i++) {
            PLACES[i].placeOnMap(googleMap);
        }
//        Marker perth = googleMap.addMarker(new MarkerOptions()
//                .title("Perth")
//                .snippet("Somewhere in Australia")
//                .position(PERTH));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "Maker clicked: "+marker.getTitle()+" "+marker.getId()+" "+marker.getSnippet());
                return false;
            }
        });
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
