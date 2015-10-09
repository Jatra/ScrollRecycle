package uk.co.jatra.scrollrecyclerchild;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<MapView> mapViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (MapView mapView : mapViews) {
            mapView.onCreate(savedInstanceState);
        }

        setContentView(R.layout.activity_main);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<SimpleViewHolder>() {
            @Override
            public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                RelativeLayout view = (RelativeLayout)inflater.inflate(R.layout.item, parent, false);
                return new SimpleViewHolder(view);
            }

            @Override
            public void onBindViewHolder(SimpleViewHolder holder, int position) {
                holder.button.setText(String.valueOf(position));
                holder.textview.setText("<<<< " + position + " >>>>");
            }

            @Override
            public int getItemCount() {
                return 3000;
            }
        };
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        for (MapView mapView : mapViews) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (MapView mapView : mapViews) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (MapView mapView : mapViews) {
            mapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (MapView mapView : mapViews) {
            mapView.onDestroy();
        }
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        public Button button;
        public TextView textview;
        public FrameLayout mapHolder;
        public SimpleViewHolder(RelativeLayout itemView) {
            super(itemView);
            button = (Button)itemView.findViewById(R.id.button);
            textview = (TextView)itemView.findViewById(R.id.textView);
            mapHolder = (FrameLayout)itemView.findViewById(R.id.mapholder);
            MapView mapView = new MapView(MainActivity.this);
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    Log.d(TAG, "map ready");
                }
            });
            mapHolder.addView(mapView);
            mapViews.add(mapView);
        }
    }
}
